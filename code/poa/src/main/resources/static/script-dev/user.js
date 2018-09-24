$(function() {
    $.poa.user = {
        initModalUsers: function() {
            $.poa.table.create({
                selector: '#modalUsersTable',
                url: 'user/userList',
                columns: [{
                    checkbox: true
                }, {
                    field: 'policeNumber',
                    title: '警号'
                }, {
                    field: 'name',
                    title: '姓名'
                }]
            });
        }
    };
    var self = $.poa.user;
});