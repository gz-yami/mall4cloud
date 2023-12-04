const files = import.meta.glob('../../icons/svg/*.svg')

const svgIcons = Object.keys(files).map((path) => {
  return path.match(/\/([^/]+)\.svg$/)[1]
})
export default svgIcons
