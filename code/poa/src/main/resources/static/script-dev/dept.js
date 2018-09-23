$(function() {
    $.poa.dept = {
        deptTree: undefined,
        init: function() {
            // init Tree
            self._initTree();
            // init button event.
            self._initButtonEvt();
        },
        initModal: function() {
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
        },
        // init dept tree.
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
                    }
                }
            });
        },
        // init button event.
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