$(function() {
    $.poa.dept = {
        deptTree: undefined,
        /**
         * 组织页面的初始化
         */
        init: function() {
            // init Tree
            self._initTree();
            // init button event.
            self._initButtonEvt();
        },
        /**
         * 组织添加/编辑的Modal对话框的初始化，以及各个控件的事件定义
         */
        initModal: function() {
            // 保存按钮事件
            $('#saveDept').click(function() {
                var deptId = $('#deptId').val();
                var deptName = $('#deptName').val();
                var deptRemark = $('#deptRemark').val();
                var parentId = $('#parentId').val();
                var isEdit = false;
                var data = {
                    name: deptName,
                    deptRemark: deptRemark
                };
                if (deptId) {
                    data.id = deptId;
                    isEdit = true;
                } else {
                    data.pId = parentId;
                }
                data.managerIdList = self._setDeptManagers();
                $.poa.ajax({
                    url: 'dept/saveDept',
                    type: 'post',
                    dataType: 'json',
                    data: data,
                    success: function() {
                        var selectedNodes = $.poa.tree.getSelectedNode(self.deptTree);
                        var parentNode;
                        $.poa.modal.destroy({
                            selector: '#deptModal'
                        });
                        if (isEdit) {
                            parentNode = $.poa.tree.getNodeById(self.deptTree, selectedNodes[0].parentTId);
                            $.poa.tree.refeshNode(self.deptTree, parentNode);
                        } else {
                            $.poa.tree.refeshNode(self.deptTree, selectedNodes[0]);
                        }
                    }
                });
            });
            // 管理者list控件。
            var deptManagers = $('#deptManagers');
            // 添加管理者事件
            $('#addManager').click(function() {
                var managers = self._setDeptManagers();
                // 弹出用户选择对话框
                $.poa.modal.create({
                    url: 'user/modalUsers',
                    data: managers
                });
            });
            // 删除管理者事件
            $('#deleteManager').click(function() {
                var selected = deptManagers.find('option:selected');
                selected.remove();
            });
        },
        /**
         * 获取页面设置的组织管理者
         * @returns {Array} 页面设置的组织管理者
         * @private
         */
        _setDeptManagers: function() {
            var selectedDeptManagers = $('#deptManagers > option');
            var userIdList = [];
            selectedDeptManagers.each(function(indx, item) {
                userIdList.push($(item).val());
            });
            return userIdList;
        },
        /**
         * 组织树的初始化
         * @private
         */
        _initTree: function() {
            self.deptTree = $.poa.tree.create({
                selector: '#deptTree',
                url: 'dept/subDept',
                checkEnabled: false,
                editEnabled: false,
                selectedMulti: false,
                callback: {
                    onNodeCreated: function(event, treeId, treeNode) {
                        if (treeNode.id === 1) {
                            self.deptTree.expandNode(treeNode, true, false, true, true);
                        }
                    },
                    beforeClick: function(treeId, treeNode, clickFlag) {
                        var selectedDeptId = $('#selectedDeptId');
                        var selectedDeptName = $('#selectedDeptName');
                        var selectedDeptRemark = $('#selectedDeptRemark');
                        var selectedDeptManagers = $('#selectedDeptManagers');
                        if (clickFlag === 0) {
                            // cancel select.
                            selectedDeptId.val('');
                            selectedDeptName.val('');
                            selectedDeptRemark.text('');
                            selectedDeptManagers.empty();
                            return false;
                        }
                        selectedDeptId.val(treeNode.id);
                        selectedDeptName.val(treeNode.name);
                        selectedDeptRemark.text(treeNode.deptRemark);
                        self._getDeptManagers(treeNode.id, selectedDeptManagers);

                        return true;
                    }
                }
            });
        },
        /**
         * 从DB中获取组织的管理者，设置到页面的List组件中
         * @param deptId 组织ID
         * @param selectedDeptManagers 页面显示管理者的List组件对象
         * @private
         */
        _getDeptManagers: function(deptId, selectedDeptManagers) {
            $.poa.ajax({
                url: 'dept/getManagers',
                type: 'get',
                dataType: 'json',
                data: {deptId: deptId},
                success: function(data) {
                    var option, user;
                    selectedDeptManagers.empty();
                    if (!data || data.length === 0) {
                        return;
                    }
                    for (var i = 0; i < data.length; i++) {
                        user = data[i];
                        option = $('<option></option>');
                        option.val(user.userId);
                        option.text(user.name);
                        option.appendTo(selectedDeptManagers);
                    }
                }
            });
        },
        /**
         * 设置树的各个按钮的动作事件
         * @private
         */
        _initButtonEvt: function() {
            var addDeptBtn = $('#addDept');
            var editDeptBtn = $('#editDept');
            var deleteDeptBtn = $('#deleteDept');

            addDeptBtn.click(function() {
                var selectedNodes = $.poa.tree.getSelectedNode(self.deptTree);
                var selectedNode;
                if (selectedNodes && selectedNodes.length > 0) {
                    selectedNode = selectedNodes[0];
                    $.poa.modal.create({
                        url: 'dept/addPage',
                        data: {
                            parentId: selectedNode.id,
                            parentName: selectedNode.name
                        }
                    });
                } else {
                    $.poa.messageBox.alert($.poa.resource.DEPT_NO_SELECTION);
                }
            });
            editDeptBtn.click(function() {
                var selectedNodes = $.poa.tree.getSelectedNode(self.deptTree);
                var selectedNode,
                    parentNode,
                    data;
                if (selectedNodes && selectedNodes.length > 0) {
                    selectedNode = selectedNodes[0];
                    parentNode = $.poa.tree.getNodeById(self.deptTree, selectedNode.parentTId);
                    data = {
                        deptId: selectedNode.id,
                        deptName: selectedNode.name,
                        deptRemark: selectedNode.deptRemark
                    };
                    if (parentNode) {
                        data.parentId = parentNode.id;
                        data.parentName = parentNode.name;
                    } else {
                        data.parentId = 0;
                        data.parentName = '';
                    }
                    $.poa.modal.create({
                        url: 'dept/editPage',
                        data: data
                    });
                } else {
                    $.poa.messageBox.alert($.poa.resource.DEPT_NO_SELECTION);
                }
            });
            deleteDeptBtn.click(function() {
                var selectedNodes = $.poa.tree.getSelectedNode(self.deptTree);
                var msg;
                if (selectedNodes && selectedNodes.length > 0) {
                    msg = $.poa.resource.DEPT_DELETE_CONFIRM + selectedNodes[0].name;
                    $.poa.messageBox.confirm(msg, $.poa.resource.CONFIRM, {
                        yes: function() {
                            $.poa.ajax({
                                url: 'dept/deleteDept',
                                type: 'post',
                                dataType: 'json',
                                data: {
                                    deptId: selectedNodes[0].id
                                },
                                success: function() {
                                    var parentNode = $.poa.tree.getNodeById(self.deptTree, selectedNodes[0].parentTId);
                                    $.poa.tree.refeshNode(self.deptTree, parentNode);
                                }
                            });
                        }
                    });
                } else {
                    $.poa.messageBox.alert($.poa.resource.DEPT_NO_SELECTION);
                }
            });
        }
    };
    var self = $.poa.dept;
});