package scalax.chart

/** Mixin solely for the purpose of providing common documentation macros.
  *
  * == Concrete ==
  *
  * The following list contains macros that are usually overridden by concrete representations:
  *
  *  - '''`\$chart`''' = $chart
  *  - '''`\$Chart`''' = $Chart
  *
  * == Basic ==
  *
  * The following list contains basic macros that are usually not overridden:
  *
  *  - '''`\$data`''' = $data
  *  - '''`\$domainAxisLabel`''' = $domainAxisLabel
  *  - '''`\$legend`''' = $legend
  *  - '''`\$orientation`''' = $orientation
  *  - '''`\$peer`''' = $peer
  *  - '''`\$rangeAxisLabel`''' = $rangeAxisLabel
  *  - '''`\$theme`''' = $theme
  *  - '''`\$title`''' = $title
  *  - '''`\$tooltips`''' = $tooltips
  *
  * @define chart chart
  * @define Chart Chart
  *
  * @define data the data the $chart will visualize
  * @define domainAxisLabel the label for the domain axis
  * @define legend whether or not the $chart will contain a legend
  * @define orientation the orientation of the $chart
  * @define peer the underlying $chart
  * @define rangeAxisLabel the label for the range axis
  * @define theme the theme to apply to the $chart
  * @define title the title of the $chart
  * @define tooltips whether or not tooltips will be generated
  */
trait DocMacros
