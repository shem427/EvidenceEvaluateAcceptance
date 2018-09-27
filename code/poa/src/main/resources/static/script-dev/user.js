$(function() {
    $.poa.user = {
        initModalUsers: function() {
            self._initModalUserTable();
            self._initButtonEvt();
        },
        _initSelected: function() {

        },
        _initButtonEvt: function() {
            $('#userSearch').click(function() {
                $.poa.table.refresh({
                    selector: '#modalUsersTable',
                    params: {
                        silent: true
                    }
                });
            });
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
                        offset:params.offset,
                        sortOrder: params.order,
                        sortField: params.sort,
                        policeNoLike: policeNoLike,
                        nameLike: nameLike
                    };
                },
                onCheck: self._tableCheck,
                onCheckAll: function(rows) {
                    if (rows && $.isArray(rows)) {
                        $.each(rows, function (indx, row) {
                            self._tableCheck(row);
                        });
                    }
                },
                onUncheck: self._tableUncheck,
                onUncheckAll: function(rows) {
                    if (rows && $.isArray(rows)) {
                        $.each(rows, function (indx, row) {
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
        }
    };
    var self = $.poa.user;
});