package scalax.chart

import event._

import scala.collection.JavaConverters._
import scala.collection.Traversable
import scala.collection.mutable.Buffer
import scala.swing.Publisher

import org.jfree.chart.{ ChartPanel, JFreeChart }
import org.jfree.chart.{ event => jevent }
import org.jfree.chart.title.Title

import com.lowagie.text.pdf.{ DefaultFontMapper, FontMapper }

import module.Imports._

/** Generic graphical representation of data.
  *
  * == Swing Integration ==
  *
  * $DisplayableChartInfo
  *
  * == Saving Charts ==
  *
  * $StorableChartInfo
  */
abstract class Chart protected () extends DisplayableChart with StorableChart with Publisher {

  /** Returns the underlying chart. */
  def peer: JFreeChart

  /** Returns the underlying plot type. */
  type Plot <: org.jfree.chart.plot.Plot

  /** Returns the underlying plot. */
  def plot: Plot

  /** Returns true if this chart is drawn with anti-aliasing. */
  def antiAlias: Boolean =
    peer.getAntiAlias

  /** Sets whether or not this chart is drawn with anti-aliasing. */
  def antiAlias_=(a: Boolean): Unit =
    peer.setAntiAlias(a)

  /** Returns the paint used for the chart background. */
  def backgroundPaint: Paint =
    peer.getBackgroundPaint

  /** Sets the paint used for the chart background. */
  def backgroundPaint_=(p: Paint): Unit =
    peer.setBackgroundPaint(p)

  /** Returns the title of this chart. */
  def title: String =
    peer.getTitle.getText

  /** Sets the title of this chart. */
  def title_=(title: String): Unit =
    peer.setTitle(title)

  /** Contains this charts subtitles and legends. */
  object subtitles extends Buffer[Title] {
    override def +=(title: Title): this.type = {
      peer.addSubtitle(title)
      this
    }

    override def +=:(title: Title): this.type = {
      peer.addSubtitle(0, title)
      this
    }

    override def apply(n: Int): Title =
      peer.getSubtitle(n)

    override def clear(): Unit =
      peer.clearSubtitles()

    override def insertAll(n: Int, elems: Traversable[Title]): Unit = {
      var i = n
      elems foreach { title ⇒
        peer.addSubtitle(i, title)
        i += 1
      }
    }

    override def iterator: Iterator[Title] =
      peer.getSubtitles.iterator.asScala.map(_.asInstanceOf[Title])

    override def length: Int =
      peer.getSubtitleCount

    override def remove(n: Int): Title = {
      val title = apply(n)
      peer.removeSubtitle(title)
      title
    }

    override def update(n: Int, newTitle: Title): Unit = {
      remove(n)
      peer.addSubtitle(n, newTitle)
    }
  }

  peer.addChangeListener(new jevent.ChartChangeListener {
    override def chartChanged(event: jevent.ChartChangeEvent): Unit = event match {
      case event: jevent.PlotChangeEvent =>
        publish(PlotChanged(Chart.this, event.getPlot))

      case event: jevent.TitleChangeEvent =>
        publish(TitleChanged(Chart.this, event.getTitle))

      case event => event.getType match {
        case jevent.ChartChangeEventType.GENERAL         => publish(ChartEvent.General(Chart.this))
        case jevent.ChartChangeEventType.NEW_DATASET     => publish(ChartEvent.NewDataset(Chart.this))
        case jevent.ChartChangeEventType.DATASET_UPDATED => publish(ChartEvent.DatasetUpdated(Chart.this))
      }
    }
  })

}

object Chart {

  object Default {
    def Height: Int = ChartPanel.DEFAULT_HEIGHT
    def Width: Int = ChartPanel.DEFAULT_WIDTH

    def Resolution: (Int,Int) = (Width,Height)

    def FontMapper: FontMapper = new DefaultFontMapper
  }

}
