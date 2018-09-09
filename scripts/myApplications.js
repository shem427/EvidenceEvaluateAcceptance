$(function() {
	if (!$.poa) {
		$.poa = {};
	}
	$.poa.mine = {
		init: function() {
			var applicationList = $('#applicationList');
			var applicationDetail = $('#applicationDetail');
			var applicationLink = $('.applicationNo');
			// button
			var revertApplication = $('#revertApplication');
			var back = $('#back');
			
			var fileSelect = $('.fileSelect');

			applicationDetail.hide();
			applicationLink.click(function(e) {
				e.preventDefault();
				applicationDetail.show();
				applicationList.hide();
			});
			revertApplication.click(self._buttonEvent);
			back.click(self._buttonEvent);
			
			fileSelect.scroll(function() {
				var pos = this.scrollTop;
				fileSelect.not($(this)).scrollTop(pos);
			});
			
		},
		_buttonEvent: function() {
			var applicationList = $('#applicationList');
			var applicationDetail = $('#applicationDetail');

			applicationDetail.hide();
			applicationList.show();
		}
	};
	var self = $.poa.mine;
});