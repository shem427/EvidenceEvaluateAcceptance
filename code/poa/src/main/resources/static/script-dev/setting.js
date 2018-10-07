$(function() {
    var self;
    $.poa.setting = {
        // ------------------------------------- 修改密码 开始-------------------------------------------------
        initChangePassword: function() {
            var changePasswordBtn = $('#changePasswordBtn');
            changePasswordBtn.click(self._changePassword);
        },
        _changePassword: function() {
            var oldPassword = $('#oldPassword');
            var newPassword = $('#newPassword');
            var newPasswordConfirm = $('#newPasswordConfirm');

            if (self._validatePassword(oldPassword, newPassword, newPasswordConfirm)) {
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
        _validatePassword: function(oldPassword, newPassword, newPasswordConfirm) {
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
        },
        // ------------------------------------- 修改密码 结束-------------------------------------------------

        // ------------------------------------- 我的账号 开始-------------------------------------------------
        initUpdateProfile: function() {
            $('#btnUpdateProfile').click(function() {
                self._validateProfile();
                $.poa.ajax({
                    url: 'setting/updateProfile',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        userId: $('#userId').val(),
                        name: $('#userName').val(),
                        policeNumber: $('#policeNumber').val(),
                        phoneNumber: $('#phone').val()
                    },
                    success: function() {
                        $.poa.messageBox.info($.poa.resource.UPDATE_PROFILE_SUCCESS);
                    }
                });
            });
        },
        _validateProfile: function() {
            var profile = {
                userId: $('#userId').val(),
                name: $('#userName').val(),
                policeNumber: $('#policeNumber').val(),
                phoneNumber: $('#phone').val()
            };
            var ret = $('#updateProfileForm').validator('validate');

            return ret;
        }
        // ------------------------------------- 我的账号 结束-------------------------------------------------
    };
    self = $.poa.setting;
});