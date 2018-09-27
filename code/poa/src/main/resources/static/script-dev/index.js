// init for parent page.
$(function() {
    $.poa.index = {
        init: function() {
            // menu link.
            var $updateProfileLink = $('#updateProfileLink');
            var $updateProfileSettingLink = $('#updateProfileSettingLink');
            var $changePasswordLink = $('#changePasswordLink');
            var $changePasswordSettingLink = $('#changePasswordSettingLink');
            var $logoutLink = $('#logoutLink');

            var $dashboardLink = $('#dashboardLink');
            var $applyIdentificationLink = $('#applyIdentificationLink');
            var $myApplicationsLink = $('#myApplicationsLink');
            var $showApplicationHistoryLink = $('#showApplicationHistoryLink');
            var $showPendingApplicationLink = $('#showPendingApplicationLink');
            var $showProcessedAppliationLink = $('#showProcessedAppliationLink');
            var $applicationListLink = $('#applicationListLink');
            var $deptLink = $('#deptLink');
            var $userLink = $('#userLink');
            var $codeLink = $('#codeLink');

            var menuItems = $('.menuItem');

            // 密码修改事件
            var changePassword = function(e) {
                e.preventDefault();
                menuItems.removeClass('active');
                $changePasswordSettingLink.addClass('active');
                self._getWrapperPage('setting/changePasswordPage');
            };
            var updateProfile = function(e) {
                e.preventDefault();
                menuItems.removeClass('active');
                $updateProfileSettingLink.addClass('active');
                self._getWrapperPage('setting/updateProfilePage');
            };

            // 首页LINK
            $dashboardLink.click(function(e) {
                e.preventDefault();
                menuItems.removeClass('active');
                $(this).addClass('active');
                self._getWrapperPage('dashboard/index');
            });

            // 用户管理LINK
            $userLink.click(function(e) {
                e.preventDefault();
                menuItems.removeClass('active');
                $(this).addClass('active');
                self._getWrapperPage('user/index');
            });

            // 组织管理LINK
            $deptLink.click(function(e) {
                e.preventDefault();
                menuItems.removeClass('active');
                $(this).addClass('active');
                self._getWrapperPage('dept/index');
            });

            // 密码修改LINK
            $changePasswordLink.click(changePassword);
            $changePasswordSettingLink.click(changePassword);

            // 修改个人资料
            $updateProfileLink.click(updateProfile);
            $updateProfileSettingLink.click(updateProfile);

            // 模块管理
            $codeLink.click(function(e) {
                e.preventDefault();
                menuItems.removeClass('active');
                $(this).addClass('active');
                self._getWrapperPage('code/index');
            });

            // 打开页面默认选中首页.
            $dashboardLink.trigger('click');
        },
        /**
         * get page wrapper contents.
         *
         * @param url - url.
         * @private
         */
        _getWrapperPage: function(url) {
            var $pageWrapper = $('#page-wrapper');
            $.poa.ajax({
                url: url,
                type: 'get',
                dataType: 'html',
                success: function(data) {
                    $pageWrapper.empty().append(data);
                }
            });
        }
    };
    var self = $.poa.index;
});