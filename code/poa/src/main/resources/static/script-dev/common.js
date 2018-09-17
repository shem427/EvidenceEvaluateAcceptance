$(function() {
    // ajax global setting for csrf.
    $.ajaxSetup({
        beforeSend : function(xhr) {
            var header = $("meta[name='_csrf_header']").attr("content");
            var token =$("meta[name='_csrf']").attr("content");
            xhr.setRequestHeader(header, token);
        },
        error: function() {
            console.log("error");
        }
    });

    $.poa = {
        contextPath: contextPath,
        storageCache: {},
        messageBox: {
            alert: function(message, title, callback) {
                var title = title || $.poa.resource.ALERT;
                $.poa.messageBox._message("alert", message, title, callback);
            },
            error: function(message, title, callback) {
                var title = title || $.poa.resource.ERROR;
                $.poa.messageBox._message("error", message, title, callback);
            },
            confirm: function(message, title, callback) {
                var title = title || $.poa.resource.CONFIRM;
                $.poa.messageBox._message("confirm", message, title, callback);
            },
            _message: function(type, message, title, callback) {
                var dialog = $("#poaMsgModal"),
                    modalType = type.toLowerCase(),
                    callbackYes,
                    callbackNo,
                    dialogHtml,
                    dialogHeader,
                    dialogContent,
                    dialogBody,
                    dialogFooter;
                title = title || "";
                if (dialog.length > 0) {
                    dialog.empty().remove();
                }

                dialogHtml =
                    "<div class='modal fade' id='poaMsgModal' tabindex='-1' role='dialog' aria-labelledby='poaMsgModalLabel' aria-hidden='true'>" +
                    "    <div class='modal-dialog'>" +
                    "        <div class='modal-content'>" +
                    "            <div class='modal-header'>" +
                    "                <button type='button' class='close' data-dismiss='modal' aria-hidden='true'><span class='fa fa-times'></span></button>" +
                    "                <h4 class='modal-title' id='poaMsgModalLabel'></h4>" +
                    "            </div>" +
                    "            <div class='modal-body'></div>" +
                    "            <div class='modal-footer'></div>" +
                    "        </div>" +
                    "    </div>" +
                    "</div>";
                dialog = $(dialogHtml);
                dialogHeader = dialog.children('h4');
                dialogBody = dialog.children(".modal-body");
                dialogContent = dialog.children(".modal-content");
                dialogFooter = dialog.children(".modal-footer");
                // create dialog
                $("body").append(dialog);

                // config dialog
                dialogHeader.html(title);
                dialogBody.html(message);
                if (modalType === 'alert') {
                    dialogContent.addClass('modal-alert');
                    dialogFooter.append('<button type="button" class="btn btn-sm btn-primary">' + $.poa.resource.OK + '</button>');
                    dialogFooter.children('button').click(function() {
                        if (callback) {
                            callback();
                        }
                        dialog.modal('hide');
                    });
                } else if (modalType === 'error') {
                    dialogContent.addClass('modal-error');
                    dialogFooter.append('<button type="button" class="btn btn-sm btn-primary">' + $.poa.resource.OK + '</button>');
                    dialogFooter.children('button').click(function() {
                        if (callback) {
                            callback();
                        }
                        dialog.modal('hide');
                    });
                } else if (modalType === 'confirm') {
                    dialogContent.addClass('modal-confirm');
                    dialogFooter.append('<button type="button" class="btn btn-sm btn-default">' + $.poa.resource.NO + '</button>');
                    dialogFooter.append('<button type="button" class="btn btn-sm btn-primary">' + $.poa.resource.YES + '</button>');
                    dialogFooter.children('button:eq(0)').click(function() {
                        if (callback && callback.no) {
                            callback.no();
                        }
                        dialog.modal('hide');
                    });
                    dialogFooter.children('button:eq(1)').click(function() {
                        if (callback && callback.yes) {
                            callback.yes();
                        }
                        dialog.modal('hide');
                    });
                }
                dialog.modal({
                    keyboard: false
                });
                dialog.on('hidden.bs.modal', function() {
                    dialog.empty().remove();
                });
                dialog.modal('show');
            }
        },
        ajax: function(options) {
            var ajaxOptions = {
                url: $.poa.contextPath + options.url,
                traditional : true,
                type: options.type || "get",
                data: options.data || {},
                dataType: options.dataType || "json",
                async: options.async === false ? false : true,
                cache: false,
                complete: function (data) {
                    if ($.isFunction(options.complete)) {
                        options.complete(data);
                    }
                },
                success: function (data) {
                    if (options.dataType === "html") {
                        try {
                            data = $.parseJSON(data);
                        } catch (ex) {
                        }
                    }
                    if (data.status === 'ERROR') {
                        $.poa.messageBox.error(data.message);
                        return;
                    } else if (data.status === 'WARNING') {
                        $.poa.messageBox.alert(data.message);
                        return;
                    }
                    if ($.isFunction(options.success)) {
                        options.success(data);
                    }
                }
            };
            $.ajax(ajaxOptions);
        },
        ajaxFileUpload: function(options, importData) {
            var ajaxOptions = {
                url : options.url,
                data : importData,
                secureuri : false,
                fileElementId : options.fileId,
                dataType : options.dataType || "json",
                success : options.success,
                complete: function (data) {
                    if ($.isFunction(options.complete)) {
                        options.complete(data);
                    }
                }
            };
            $.ajaxFileUpload(ajaxOptions);
        },
        storage: {
            get: function(name) {
                var cache = $.poa.storageCache[name];
                var storage = window.localStorage;
                if (cache) {
                    return cache;
                }
                if (storage) {
                    cache = storage[name];
                    if (cache) {
                        $.poa.storageCache[name] = cache;
                    }
                    return cache;
                } else {
                    return undefined;
                }
            },
            set: function(name, value) {
                var storage = window.localStorage;
                $.poa.storageCache[name] = value;
                if (storage) {
                    storage[name] = value;
                }
            }
        },
        tree: {
            create: function(options, data) {
                var zNodes;
                var $tree = $(options.selector);
                var setting = {
                    check: {
                        enable: options.checkEnabled !== false
                    },
                    edit: {
                        enable: options.editEnabled !== false
                    },
                    view: {
                        selectedMulti: options.selectedMulti !== false
                    },
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: 'id',
                            pIdKey: 'pId',
                            rootPId: 0
                        }
                    }
                };
                if (data) {
                    zNodes = data;
                } else {
                    zNodes = [];
                }
                $.fn.zTree.init($tree, setting, zNodes);
            }
        }
    };
});