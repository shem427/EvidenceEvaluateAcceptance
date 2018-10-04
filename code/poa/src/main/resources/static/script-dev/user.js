$(function() {
    var self;
    $.poa.user = {
        // ------------------------------------- 人员管理页面 开始-------------------------------------------------
        init: function() {
            self._initUserTable();
            self._initSearchEvt('#usersTable');
            self._initLinkEvt();
        },
        _initUserTable: function() {
            $.poa.table.create({
                selector: '#usersTable',
                url: 'user/userList',
                sortName: 'POLICE_NUMBER',
                pageSize: 5,
                pageList: [5, 10, 20, 50],
                columns: [{
                    checkbox: true
                }, {
                    field: 'policeNumber',
                    title: '警号'
                }, {
                    field: 'name',
                    title: '姓名'
                }],
                queryParams: function(params) {
                    var policeNoLike = $('#policeNo').val();
                    var nameLike = $('#userName').val();
                    return {
                        limit: params.limit,
                        offset: params.offset,
                        sortOrder: params.order,
                        sortField: params.sort,
                        policeNoLike: policeNoLike,
                        nameLike: nameLike
                    };
                }
            });
        },
        _initSearchEvt: function(tableSelector) {
            $('#userSearch').click(function() {
                $.poa.table.refresh({
                    selector: tableSelector,
                    params: {
                        silent: true
                    }
                });
            });
        },
        _initLinkEvt: function() {
            // 导入文件
            var importFileLink = $('#importFileLink');
            // 导出全部
            var exportAllLink = $('#exportAllLink');
            // 导出本页
            var exportCurrentPageLink = $('#exportCurrentPageLink');
            // 导出选择
            var exportSelectionLink = $('#exportSelectionLink');
            // 添加
            var addUser = $('#addUser');
            // 编辑
            var editUser = $('#editUser');
            // 删除
            var deleteUser = $('#deleteUser');

            addUser.click(function() {
                $.poa.modal.create({
                    url: 'user/addPage'
                });
            });
            editUser.click(function() {
                var selections = $.poa.table.getSelections('#usersTable');
                var selected;
                if (selections.length === 0) {
                    $.poa.messageBox.alert($.poa.resource.USER_NO_SELETION);
                    return;
                } else if (selections.length !== 1) {
                    $.poa.messageBox.alert($.poa.resource.USER_EDIT_MULTI_SELECT);
                    return;
                }
                selected = selections[0];
                $.poa.modal.create({
                    url: 'user/editPage',
                    data: {
                        userId: selected.userId,
                        policeNumber: selected.policeNumber,
                        phoneNumber: selected.phoneNumber,
                        name: selected.name,
                        deptId: selected.deptId,
                        deptName: selected.deptName
                    }
                });
            });
            deleteUser.click(function() {
                var selections = $.poa.table.getSelections('#usersTable');
                var userIdList = [];
                $.each(selections, function(indx, item) {
                    userIdList.push(item.userId);
                });
                if (userIdList.length === 0) {
                    $.poa.messageBox.alert($.poa.resource.USER_NO_SELETION);
                    return;
                }
                $.poa.messageBox.confirm($.poa.resource.USER_DELETE_CONFIRM + userIdList.length, '', {
                    yes: function() {
                        $.poa.ajax({
                            url: 'user/delete',
                            type: 'post',
                            dataType: 'json',
                            contentType: 'application/json',
                            data: JSON.stringify(userIdList),
                            success: function() {
                                $('#userSearch').trigger('click');
                                $.poa.messageBox.info($.poa.resource.USER_DELETE_SUCCESS);
                            }
                        });
                    }
                });
            });
        },
        // ------------------------------------- 人员管理页面 结束-------------------------------------------------

        // ------------------------------------- 人员选择Modal 开始-----------------------------------------------
        initModalUsers: function() {
            self._initModalUserTable();
            self._initSearchEvt('#modalUsersTable');
        },
        _initModalUserTable: function() {
            $.poa.table.create({
                selector: '#modalUsersTable',
                url: 'user/userList',
                sortName: 'POLICE_NUMBER',
                pageSize: 5,
                pageList: [5, 10, 20, 50],
                columns: [{
                    checkbox: true
                }, {
                    field: 'policeNumber',
                    title: '警号'
                }, {
                    field: 'name',
                    title: '姓名'
                }],
                queryParams: function(params) {
                    var policeNoLike = $('#policeNo').val();
                    var nameLike = $('#userName').val();
                    return {
                        limit: params.limit,
                        offset: params.offset,
                        sortOrder: params.order,
                        sortField: params.sort,
                        policeNoLike: policeNoLike,
                        nameLike: nameLike
                    };
                },
                onCheck: self._tableCheck,
                onCheckAll: function(rows) {
                    if (rows && $.isArray(rows)) {
                        $.each(rows, function(indx, row) {
                            self._tableCheck(row);
                        });
                    }
                },
                onUncheck: self._tableUncheck,
                onUncheckAll: function(rows) {
                    if (rows && $.isArray(rows)) {
                        $.each(rows, function(indx, row) {
                            self._tableUncheck(row);
                        });
                    }
                },
                onLoadSuccess: function(data) {
                    var selectedUserIds;
                    if (data.rows.length === 0) {
                        return;
                    }
                    selectedUserIds = self._getSelectedUsers();
                    $.each(data.rows, function(indx, item) {
                        var userId = item.userId;
                        if ($.inArray(userId.toString(), selectedUserIds) >= 0) {
                            $.poa.table.check('#modalUsersTable', indx);
                        }
                    });
                }
            });
        },
        _tableCheck: function(row) {
            var selectedUsersList = $('#selectedUsers');
            var option;
            var selectedUserIds = self._getSelectedUsers();
            if ($.inArray(row.userId.toString(), selectedUserIds) >= 0) {
                return;
            }
            option = $('<option></option>');
            option.val(row.userId);
            option.text(row.name + '(' + row.policeNumber + ')');
            option.appendTo(selectedUsersList);
        },
        _tableUncheck: function(row) {
            var selectedUsersList = $('#selectedUsers');
            var options = selectedUsersList.find('option');
            options.each(function(indx, item) {
                var opt = $(item);
                if (opt.val() === row.userId.toString()) {
                    opt.remove();
                }
            });
        },
        _getSelectedUsers: function() {
            var selectedUserIds = [];
            var selectedUserOptions = $('#selectedUsers > option');
            var userOption;
            if (!selectedUserOptions || selectedUserOptions.length === 0) {
                return selectedUserIds;
            }
            for (var i = 0; i < selectedUserOptions.length; i++) {
                userOption = $(selectedUserOptions[i]);
                selectedUserIds.push(userOption.val());
            }
            return selectedUserIds;
        },
        // ------------------------------------- 人员选择Modal 结束-----------------------------------------------

        // ------------------------------------- 人员添加/编辑Modal 开始-------------------------------------------
        initModal: function() {
            $('#selectDept').click(function() {

            });
            $('#saveUser').click(function() {
                $.poa.ajax({
                    url: 'user/save',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        userId: $('#userId').val(),
                        policeNumber: $('#policeNumber').val(),
                        phoneNumber: $('#phoneNumber').val(),
                        name: $('#userName').val(),
                        deptId: $('#deptId').val()
                    }
                });
            });
        }
        // ------------------------------------- 人员添加/编辑Modal 结束-------------------------------------------
    };
    self = $.poa.user;
});