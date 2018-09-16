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

            // 密码修改事件
            var changePassword = function(e) {
                e.preventDefault();
                self._getWrapperPage(contextPath + 'setting/changePassword');
            };

            // 首页LINK
            $dashboardLink.click(function(e) {
                e.preventDefault();
                self._getWrapperPage(contextPath + 'dashboard/index');
            });

            // 用户管理LINK
            $userLink.click(function(e) {
                e.preventDefault();
                self._getWrapperPage(contextPath + 'user/index');
            });

            // 组织管理LINK
            $deptLink.click(function(e) {
                e.preventDefault();
                self._getWrapperPage(contextPath + 'dept/index');
            });

            // 密码修改LINK
            $changePasswordLink.click(changePassword);
            $changePasswordSettingLink.click(changePassword);

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