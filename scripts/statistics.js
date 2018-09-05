$(function() {
	if (!$.poa) {
		$.poa = {};
	}
	
	$.poa.statistics = {
		init: function() {
			// init data.
			var $beginDate = $('#beginDate');
			var $endDate = $('#endDate');
			if (!$beginDate.val()) {
				$beginDate.val(self._getDate(0, -1, 0));
			}
			if (!$endDate.val()) {
				$endDate.val(self._getDate(0, 0, 0));
			}
			$('#dataCount').click(function() {
				var begin = $beginDate.val();
				var end = $endDate.val();
				self._showRegionCountChart(begin, end);
				self._showTypeCountChart(begin, end);
			});
		},
		_getDate: function(yearOffset, monthOffset, dayOffset) {
			var now = new Date();
			var year = now.getFullYear() + yearOffset;
			var month = now.getMonth() + monthOffset;
			var day = now.getDay() + dayOffset;
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
		},
		_showRegionCountChart: function(begin, end) {
			var data = self._getRegionCountData(begin, end);
			var plotGrid = {
				borderWidth: 1,
				backgroundColor: {
					colors: ["#fff", "#e4f4f4"]
				},
				hoverable: true
			};
			var xaxis = {
				tickFormatter: function() {
					return "";
				}
			};
			$.plot($('#region-chart'), [{
				data: data,
				bars: {
					show: true,
					barWidth: 0.6,
					align: "center"
				}
			}], {
				grid: plotGrid,
				xaxis: xaxis,
				legend: {
					show: true
				},
				tooltip: true,
				tooltipOpts: {
					content: '%y'
				}
			});
		},
		_showTypeCountChart: function(begin, end) {
			var data = self._getTypeCountData(begin, end);
			$.plot($('#type-chart'), data, {
				series: {
					pie: { 
						innerRadius: 0.3,
						show: true
					}
				},
				grid: {
					hoverable: true,
					clickable: true
				},
				legend: {
					show: false
				}
			});
		},
		_getRegionCountData: function(begin, end) {
			// dummy data.
			var data = []
			for (var i = 0; i < 10; i++) {
				data.push([i, Math.random() * 48]);
			}
			return data;
		},
		_getTypeCountData: function(begin, end) {
			// dummy data.
			return [
				{label: '类型1', data: Math.random() * 50},
				{label: '类型2', data: Math.random() * 50},
				{label: '类型3', data: Math.random() * 50},
			];
		}
	};
	var self = $.poa.statistics;
});