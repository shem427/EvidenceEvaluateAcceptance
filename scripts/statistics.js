$(function() {
    if (!$.poa) {
        $.poa = {};
    }
    
    $.poa.statistics = {
        init: function() {
            // init data.
			var $dateControls = $('.date-control');
            var $beginDate = $('#beginDate');
            var $endDate = $('#endDate');
			
			$dateControls.datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				todayHighlight: 1,
				minView: "month",
				autoclose: 1
			});
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
        },
        _showRegionCountChart: function(begin, end) {
            var data = self._getRegionCountData(begin, end);
			var ticks = [
				[0, '区1'],
				[1, '区2'],
				[2, '区3'],
				[3, '区4'],
				[4, '区5'],
				[5, '区6'],
				[6, '区7'],
				[7, '区8'],
				[8, '区9'],
				[9, '区10'],
			];
            var options = {
                series: {
                    bars: {
                        show: true
                    }
                },
                bars: {
                    align: 'center',
                    barWidth: 0.5
                },
                xaxis: {
                    ticks: ticks
                },
				grid: {
					borderWidth: 1,
					backgroundColor: {
						colors: ["#fff", "#e4f4f4"]
					},
					hoverable: true
				},
				legend: {
                    noColumns: 0,
	                labelBoxBorderColor: "#000000",
	                position: "nw"
                },
				tooltip: true,
				tooltipOpts: {
					content: '%y'
				}
            };
            $.plot($('#region-chart'), [data], options);
            /*var plotGrid = {
                borderWidth: 1,
                backgroundColor: {
                    colors: ["#fff", "#e4f4f4"]
                },
                hoverable: true
            };
            var xaxis = {
                mode: 'categories',
                tickLength: 0
            };
            $.plot($('#region-chart'), [data], {
                series: {
                    bars: {
                        show: true,
                        barWidth: 0.6,
                        align: "center",
                    }
                },
                grid: plotGrid,
                xaxis: xaxis,
                legend: {
                    show: true
                },
                tooltip: true,
                tooltipOpts: {
                    content: '%y'
                }
            });*/
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
			var number;
            for (var i = 0; i < 10; i++) {
				number = Math.round(Math.random() * 48);
                data.push([i, number]);
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