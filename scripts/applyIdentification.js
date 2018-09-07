$(function() {
	if (!$.poa) {
		$.poa = {};
	}
	$.poa.apply = {
		init: function() {
			var caseTime = $('#caseTime');
			var otherMaterialTypeArea = $('.otherMaterialTypeArea');
			var btnDate = $('.btn-date');
			
			caseTime.datetimepicker({
				minuteStep: 1,
				language: 'zh-CN',
				format: 'yyyy-mm-dd hh:ii',
				todayHighlight: 1,
				autoclose: 1
			});

			btnDate.click(function() {
				var $this = $(this);
				var dateControl = $this.parent().prev();
				dateControl.datetimepicker('show');
			});
			
			otherMaterialTypeArea.hide();
			$('.materialType').change(function() {
				var $this = $(this);
				if ($this.val() === 'other') {
					otherMaterialTypeArea.show();
				} else {
					otherMaterialTypeArea.hide();
				}
			});
		}
	};
});