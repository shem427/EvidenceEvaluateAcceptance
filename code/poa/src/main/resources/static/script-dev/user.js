$(function() {
    $.poa.user = {
        initModalUsers: function() {
            $.poa.table.create({
                selector: '#modalUsersTable',
                url: 'user/userList',
                sortName: 'POLICE_NUMBER',
                pageSize: 5,
                pageList: [5, 10, 20, 50],
                columns: [{
                    checkbox: true,
                    formatter: function(value, row, index) {
                        // TODO:
                    }
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

                },
                onUncheck: function(row) {

                }
            });
            $('#userSearch').click(function() {
                $.poa.table.refresh({
                    selector: '#modalUsersTable',
                    params: {
                        silent: true
                    }
                });
            });
        },
        _additionalCondition: function() {
            return {
                policeNoLike: $('#policeNo').val(),
                nameLike: $('#userName').val()
            };
        }
    };
    var self = $.poa.user;
});