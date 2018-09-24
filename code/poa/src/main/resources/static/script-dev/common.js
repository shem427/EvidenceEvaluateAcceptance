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
        modal: {
            create: function(options) {
                var ajaxOptions = {
                    url: $.poa.contextPath + options.url,
                    traditional : true,
                    type: options.type || "get",
                    data: options.data || {},
                    dataType: options.dataType || "html",
                    async: options.async === false ? false : true,
                    cache: false,
                    success: function (data) {
                        var dialog = $(data);
                        // create dialog
                        $("body").append(dialog);
                        dialog.modal({
                            keyboard: false,
                            backdrop: false
                        });
                        dialog.on('hidden.bs.modal', function() {
                            dialog.empty().remove();
                        });
                        dialog.modal('show');
                    }
                };
                $.ajax(ajaxOptions);
            },
            destroy: function(options) {
                var dialog = $(options.selector);
                dialog.modal('hide');
            }
        },
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
            info: function(message, title, callback) {
                var title = title || $.poa.resource.INFO;
                $.poa.messageBox._message("info", message, title, callback);
            },
            _message: function(type, message, title, callback) {
                var dialog = $("#poaMsgModal"),
                    modalType = type.toLowerCase(),
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
                    "                <h4 class='modal-title' id='poaMsgModalLabel'></h4>" +
                    "            </div>" +
                    "            <div class='modal-body'></div>" +
                    "            <div class='modal-footer'></div>" +
                    "        </div>" +
                    "    </div>" +
                    "</div>";
                dialog = $(dialogHtml);
                // create dialog
                $("body").append(dialog);

                dialogHeader = dialog.find('h4');
                dialogBody = dialog.find(".modal-body");
                dialogContent = dialog.find(".modal-content");
                dialogFooter = dialog.find(".modal-footer");

                // config dialog
                dialogBody.html(message);
                if (modalType === 'alert') {
                    dialogHeader.html('<i class="fa fa-warning fa-fw"></i>' + title);
                    dialogContent.addClass('modal-alert');
                    dialogFooter.append('<button type="button" class="btn btn-sm btn-primary">' + $.poa.resource.OK + '</button>');
                    dialogFooter.children('button').click(function() {
                        if (callback) {
                            callback();
                        }
                        dialog.modal('hide');
                    });
                } else if (modalType === 'error') {
                    dialogHeader.html('<i class="fa fa-window-close-o fa-fw"></i>' + title);
                    dialogContent.addClass('modal-error');
                    dialogFooter.append('<button type="button" class="btn btn-sm btn-primary">' + $.poa.resource.OK + '</button>');
                    dialogFooter.children('button').click(function() {
                        if (callback) {
                            callback();
                        }
                        dialog.modal('hide');
                    });
                } else if (modalType === 'confirm') {
                    dialogHeader.html('<i class="fa fa-question-circle-o fa-fw"></i>' + title);
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
                } else if (modalType === 'info') {
                    dialogHeader.html('<i class="fa fa-info-circle fa-fw"></i>' + title);
                    dialogContent.addClass('modal-info');
                    dialogFooter.append('<button type="button" class="btn btn-sm btn-primary">' + $.poa.resource.OK + '</button>');
                    dialogFooter.children('button').click(function() {
                        if (callback) {
                            callback();
                        }
                        dialog.modal('hide');
                    });
                }
                dialog.modal({
                    keyboard: false,
                    backdrop: false
                });
                dialog.on('hidden.bs.modal', function() {
                    dialog.empty().remove();
                });
                dialog.on('shown.bs.modal', function() {
                    dialog.find('button.btn-primary').focus();
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
                url : $.poa.contextPath + options.url,
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
                    },
                    async: {
                        url: $.poa.contextPath + options.url,
                        autoParam: ['id'],
                        type: options.type || 'get',
                        enable: options.url,
                        dataType: 'json',
                        dataFilter: options.dataFilter || null
                    },
                    callback: options.callback
                };
                if (data) {
                    zNodes = data;
                } else {
                    zNodes = [];
                }
                return $.fn.zTree.init($tree, setting, zNodes);
            },
            getSelectedNode: function(zTree) {
                return zTree.getSelectedNodes();
            },
            getNodeById: function(zTree, id) {
                return node = zTree.getNodeByTId(id);
            },
            refeshNode: function(zTree, node) {
                zTree.reAsyncChildNodes(node, 'refresh', false);
            }
        },
        table: {
            create: function(options) {
                var $table = $(options.selector);
                $table.bootstrapTable({
                    url: $.poa.contextPath + options.url,
                    method: options.method || 'get',
                    toolbar: options.toolbar || '#toolbar',
                    striped: true,
                    cache: options.cache === true,
                    pagination: options.pagination !== false,
                    sortable: options.sortable !== false,
                    sortOrder: 'asc',
                    queryParams: function(params) {
                        var qParam = {
                            limit: params.limit,
                            offset:params.offset,
                            sortOrder: params.sortOrder,
                            sortField: params.sortField
                        };
                        return qParam;
                    },
                    sidePagination: 'server',
                    pageNumber: 1,
                    pageSize: 10,
                    pageList: options.pageList || [10, 25, 50, 100],
                    search: options.search === true,
                    contentType: 'application/json',
                    dataType: 'json',
                    strictSearch: true,
                    showColumns: true,
                    showRefresh: options.showRefresh !== false,
                    clickToSelect: false,
                    uniqueId: options.uniqueId,
                    showToggle: false,
                    cardView: false,
                    detailView: false,
                    columns: options.columns,
                    undefinedText: $.poa.resource.TABLE_NO_DATA,
                    rowStyle: options.rowStyle,
                    onLoadSuccess: options.onLoadSuccess,
                    onLoadError: options.onLoadError
                });
            }
        }
    };
});