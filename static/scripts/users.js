$(function() {
	if (!$.poa) {
		$.poa = {};
	}
	$.poa.users = {
		init: function() {
			// init Tree
			self._initTree();
		},
		_initTree: function() {
			var tree = $('#deptTree');
			var setting = {
				view: {
					selectedMulti: false
				},
				check: {
					enable: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				edit: {
					enable: false
				},
				callback: {
					onClick: self._clickNode
				}
			};
			var zNodes = [
				{ id:1, pId:0, name:"合肥市公安局", open:true},
				{ id:11, pId:1, name:"包河区公安局"},
				{ id:111, pId:11, name:"AAA派出所"},
				{ id:112, pId:11, name:"BBB刑警大队"},
				{ id:113, pId:11, name:"CCC交警大队"},
				{ id:114, pId:11, name:"DDD-----DDD"},
				{ id:12, pId:1, name:"蜀山区公安局"},
				{ id:121, pId:12, name:"EEE派出所"},
				{ id:122, pId:12, name:"FFF刑警大队"},
				{ id:123, pId:12, name:"GGG交警大队"},
				{ id:124, pId:12, name:"HHHH---HHH"}
			];
			$.fn.zTree.init(tree, setting, zNodes);
		},
		_clickNode: function(event, treeId, treeNode, clickFlag) {
			$('#dept').val(treeNode.name);
		}
	};
	var self = $.poa.users;
});