$(function() {
    $.poa.setting = {
        initChangePassword: function() {
            var changePasswordBtn = $('#changePasswordBtn');
            changePasswordBtn.click(self._changePassword);
        },
        _changePassword: function() {
            var oldPassword = $('#oldPassword');
            var newPassword = $('#newPassword');
            var newPasswordConfirm = $('#newPasswordConfirm');

            if (self._validation(oldPassword, newPassword, newPasswordConfirm)) {
                $.poa.ajax({
                    url: 'setting/changePassword',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        oldPassword: oldPassword.val(),
                        newPassword: newPassword.val()
                    },
                    success: function() {
                        oldPassword.val('');
                        newPassword.val('');
                        newPasswordConfirm.val('');
                        $.poa.messageBox.info($.poa.resource.CHANGE_PASSWORD_SUCCESS);
                    }
                });
            }
        },
        _validation: function(oldPassword, newPassword, newPasswordConfirm) {
            if (newPassword.val() !== newPasswordConfirm.val()) {
                // 新密码与新密码确认必须一致。
                $.poa.messageBox.alert($.poa.resource.NEW_PASSWORD_NOT_MATCH, '', function() {
                    oldPassword.val('');
                    newPassword.val('');
                    newPasswordConfirm.val('');
                });
                return false;
            } else if (oldPassword.val() === newPassword.val()) {
                // 新密码与旧密码必须不一致。
                $.poa.messageBox.alert($.poa.resource.OLD_NEW_PASSWORD_SAME, '', function() {
                    oldPassword.val('');
                    newPassword.val('');
                    newPasswordConfirm.val('');
                });
                return false;
            }
            return true;
        }
    };
    var self = $.poa.setting;
});