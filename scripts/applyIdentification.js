$(function() {
	if (!$.poa) {
		$.poa = {};
	}
	$.poa.apply = {
		init: function() {
			var otherMaterialTypeArea = $('#otherMaterialTypeArea');
			otherMaterialTypeArea.hide();
			$('#materialType').change(function() {
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