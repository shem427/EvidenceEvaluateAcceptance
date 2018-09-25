$(function() {
    $.poa.user = {
        initModalUsers: function() {
            $.poa.table.create({
                selector: '#modalUsersTable',
                url: 'user/userList',
                sortName: 'POLICE_NUMBER',
                columns: [{
                    checkbox: true
                }, {
                    field: 'policeNumber',
                    title: '警号'
                }, {
                    field: 'name',
                    title: '姓名'
                }],
                ajaxOption: {
                    data: self._additionalCondition()
                }
            });
            $('#userSearch').click(function() {
                alert('test');
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