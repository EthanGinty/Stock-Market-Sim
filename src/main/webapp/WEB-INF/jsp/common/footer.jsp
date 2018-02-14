
<script>
	$(document).ready(
		function() {

			var docHeight = $(window).height();
			var footerHeight = $('#footer').height();
			var footerTop = $('#footer').position().top + footerHeight;

			if (footerTop < docHeight) {
				$('#footer').css('margin-top',
						10 + (docHeight - footerTop) + 'px');
			}
		});
</script>

<footer id="footer" class="footer"> Copyright 2018 Bravo, Inc. </footer>
