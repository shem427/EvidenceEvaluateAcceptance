$(function() {
	if (!$.poa) {
		$.poa = {};
	}
	$.poa.list = {
		init: function() {
			var applicationList = $('#applicationList');
			var applicationDetail = $('#applicationDetail');
			var applicaitonCondition = $('#searchCondition');
			var applicationLink = $('.applicationNo');
			// button
			var revertApplication = $('#revertApplication');
			var back = $('#back');
			
			var fileSelect = $('.fileSelect');
			
			// init data.
			var $btnDate = $('.btn-date');
			var $beginDate = $('#beginDate');
			var $endDate = $('#endDate');
			var $dateControls = $('#beginDate, #endDate');

			applicationDetail.hide();
			applicationLink.click(function(e) {
				e.preventDefault();
				applicationDetail.show();
				applicaitonCondition.hide();
				applicationList.hide();
			});
			revertApplication.click(self._buttonEvent);
			back.click(self._buttonEvent);
			
			fileSelect.scroll(function() {
				var pos = this.scrollTop;
				fileSelect.not($(this)).scrollTop(pos);
			});
			
			$dateControls.datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				todayHighlight: 1,
				minView: "month",
				autoclose: 1
			});
			$btnDate.click(function() {
				var $this = $(this);
				var dateControl = $this.parent().prev();
				dateControl.datetimepicker('show');
			});

            if (!$beginDate.val()) {
                $beginDate.val(self._getDate(0, -1, 0));
            }
            if (!$endDate.val()) {
                $endDate.val(self._getDate(0, 0, 0));
            }			
		},
		_buttonEvent: function() {
			var applicationList = $('#applicationList');
			var applicationDetail = $('#applicationDetail');
			var applicaitonCondition = $('#searchCondition');

			applicationDetail.hide();
			applicationList.show();
			applicaitonCondition.show();
		},
		_getDate: function(yearOffset, monthOffset, dayOffset) {
			var now = new Date();
			var year = now.getUTCFullYear() + yearOffset;
			var month = now.getUTCMonth() + monthOffset + 1;
			var day = now.getUTCDate() + dayOffset;
			var ret = year + '-';
			if (month < 10) {
				ret += '0' + month + '-';
			} else {
				ret += month + '-';
			}
			if (day < 10) {
				ret += '0' + day;
			} else {
				ret += day;
			}
			return ret;
		}
	};
	var self = $.poa.list;
});