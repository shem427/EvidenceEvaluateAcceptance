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
            var selectedUsersList = $('#selectedUsers');
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
                onCheck: function(row) {
                    var option = $('<option></option>');
                    option.val(row.userId);
                    option.text(row.name + '(' + row.policeNumber + ')');
                    option.appendTo(selectedUsersList);
                },
                onUncheck: function(row) {
                    var options = selectedUsersList.find('option');
                    options.each(function(indx, item) {
                        var opt = $(item);
                        if (opt.val() === row.userId) {
                            opt.remove();
                            return false;
                        }
                    });
                }
            });
        }
    };
    var self = $.poa.user;
});