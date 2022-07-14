<script>
export default {
  created() {
    console.log(111)
    this.$router.replace({ path: '/order/order' })
  },
  render: function(h) {
    return h() // avoid warning message
  }
}
</script>
