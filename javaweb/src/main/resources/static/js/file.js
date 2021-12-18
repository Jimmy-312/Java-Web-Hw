function reBind() {
    $tbody = $("#tbody")
    $("#checkAll").on("click", function (event) {
        $tbody.find('input').prop('checked', $(this).prop('checked'));
        $("#filedelete_btn").prop("disabled", $tbody.find('input[type="checkbox"]:checked').length == 0)
        event.stopPropagation();
    });

    $tbody.on("click", function (e) {
        $("#checkAll").prop('checked', $(this).find('input[type="checkbox"]:checked').length == $(this).children('tr').length);
        $("#filedelete_btn").prop("disabled", $(this).find('input[type="checkbox"]:checked').length == 0)
        e.stopPropagation();
    });
    // $td = $tbody//.find("tr")// td").not(".func");
    // $("#tbody tr td").click(function(e){
    //     console.log($td.length)
    //     $(this).parent().find("input:checked").click();
    //     e.stopPropagation();
    // });
    $tbody.find("tr td").not(".func").each(function (e) {
        $(this).attr("onclick", "tdclick(this)");
    })


}
reBind()
loadTags()
loadAuth()

function tdclick(e) {
    var $temp = $(e);
    $temp.parent().find("input[type='checkbox']").click()
    //console.log($temp)
}

function openFile(fileid) {
    window.location.href = "/editor/" + fileid;
}

function loadTags() {
    //console.log($tagspan.html())
    $files = $("#tbody").find(".tag");
    $files.each(function (e) {
        var formData = new FormData();
        $tag = $(this);
        $tag.html("")
        //console.log($tag[0].classList[2]);
        formData.append("fileid", $tag[0].classList[2])
        $.ajax({
            type: "POST",
            url: "/gettags",
            async: false,
            data: formData,
            processData: false,
            contentType: false,
            success: function (result) {
                var taglist = result.split(',');
                //console.log(taglist)
                for (var j = 0; j < taglist.length; j++) {
                    $tagspan = $('<a class="badge tagspan badge-primary"></a>');
                    $tagspan.text(taglist[j]);
                    //console.log($tagspan)
                    $tag.append($tagspan.prop("outerHTML"));
                }

                reBind()
            }
        })

    })
}

function loadAuth() {
    $auths = $("#tbody").find(".auth");
    $auths.each(function (e) {
        var formData = new FormData();
        $auth = $(this);
        formData.append("fileid", $auth[0].classList[2])
        $.ajax({
            type: "POST",
            url: "/getauth",
            async: false,
            data: formData,
            processData: false,
            contentType: false,
            success: function (result) {
                var $authtag = $("<span></span>")
                $authtag.text(result)
                $auth.html($authtag)
                if (result == "Super") {
                    $auth.append("<i class=\"bi bi-wrench\" onclick=\"tdclick($(this).parent());$(\'#authbox\').modal();getAuthUser(" + $auth[0].classList[2] + ");\" style=\"margin-left:15px\"></i>")
                    $authtag.addClass("badge badge-danger")
                } else
                    if (result == "Viewer") {
                        $auth.append("<i class=\"bi bi-x-circle\" onclick=\"tdclick($(this).parent());removemyauth(" + $auth[0].classList[2] + ",this);\" style=\"margin-left:10px\"></i>")
                        $authtag.addClass("badge badge-warning")
                    } else
                        if (result == "Editor") {
                            $auth.append("<i class=\"bi bi-x-circle\" onclick=\"tdclick($(this).parent());removemyauth(" + $auth[0].classList[2] + ");\" style=\"margin-left:15px\"></i>")
                            $authtag.addClass("badge badge-success")
                        }else{

                            $authtag.addClass("badge badge-secondary")
                        }

                reBind()
            }
        })

    })
}

function removemyauth(fileid,th) {
    //console.log(fileid)
    var formData = new FormData();
    formData.append("fileid", fileid);
    //formData.append("name", username);
    $.ajax({
        type: "POST",
        url: "/delauth",
        data: formData,
        processData: false,
        contentType: false,
        success: function (result) {
            //getAuthUser(fileid);
            $(th).parent().parent().remove()
        }
    })
}

$("#inputuser").keypress(function (e) {
    if (e.which == 13) {
        changeAuth();
        return false;
    }
});

function changeAuth() {
    //$("#authform")[0].reset();
    var $authform = $("#authform");
    //console.log($authform.serialize())
    $.ajax({
        type: "POST",
        url: "/changeauth",
        //async: false,
        data: $authform.serialize(),
        success: function (result) {
            $.each(result, function (i, n) {
                if (i == "error") {
                    console.log(n)
                } else {
                    //console.log(n)
                    getAuthUser(n);
                    //$("#inputuser").prop("disabled",true)
                }
            })
        }
    })
}

function getAuthUser(fileid) {
    var $ulist = $("#userlist");
    $("#inputuser").val("")
    $("#authfileid").val(fileid)
    var formData = new FormData();

    $ulist.html("")
    //console.log($tag[0].classList[2]);
    formData.append("fileid", fileid);
    $.ajax({
        type: "POST",
        url: "/getauthuser",
        //async: false,
        data: formData,
        processData: false,
        contentType: false,
        success: function (result) {
            $.each(result, function (i, n) {
                $span = $('<a class="badge" onclick="removeAuth(' + fileid + ',\'' + i + '\')"></a>');
                if (n == "Super") {
                    return
                } else
                    if (n == "Viewer") {
                        $span.addClass("badge-warning")
                    } else
                        if (n == "Editor") {
                            $span.addClass("badge-success")
                        }
                $span.text(i);
                $ulist.append($span.prop("outerHTML"))
            })
        }
    })
}

function removeAuth(fileid, username) {
    var formData = new FormData();
    formData.append("fileid", fileid);
    formData.append("name", username);
    $.ajax({
        type: "POST",
        url: "/delauth",
        data: formData,
        processData: false,
        contentType: false,
        success: function (result) {
            getAuthUser(fileid);
        }
    })
}

function refreshtaglist() {
    var path = window.location.pathname.replace("/", "");
    $.ajax({
        type: "POST",
        url: "/" + path + "/" + "refreshTaglist",
        data: '',
        success: function (result) {
            $("#taglist").html(result)
            reBind()
            loadTags()
            loadAuth()
        }
    })
}

function switch_tag(tagname, op,c=0) {
    $("#allinfo").val(tagname)
    if(c==1){
        data="oc=1"
    }else{
        data="oc=0"
    }
    $.ajax({
        type: "POST",
        url: "/" + op + "/" + tagname,
        data: data,
        success: function (result) {
            $("#fileform").html(result)
            loadTags()
            loadAuth()
            reBind()
            $("#filedelete_btn").prop("disabled", true)

        }
    })

}

function initAdd(type) {
    $("#newfileform")[0].reset();
    resetAdd();
    $("#btnadd").prop("disabled", true)
    $("#boxLabel").text("New File")
    $("#btnadd").text("New")
    $("#btnadd").attr("onclick", "$('#newfileform').submit()")

    $("#tags0").val("")
    //console.log(type)
    $("#addtype").val(type)
    $("#addnameback").text('.' + $("#addtype").val())

    $("#pub").prop("checked", false)
}

function editTags(e) {
    $("#newfileform")[0].reset()
    resetAdd()
    $("#btnadd").attr("onclick", "editSubmit()")
    $("#boxLabel").text("File Edit")
    $("#btnadd").text("Edit")
    $('#newbox').modal()
    var $temp = e;
    $("#tags0").val($temp.find("td[name='name']").text())
    $("#addtype").val($temp.find("td[name='type']").attr("class"))
    $("#addnameback").text('.' + $("#addtype").val())
    $("#addid").val($temp.find("input[type='checkbox']").val())
    //console.log($temp.find("td[name='public']").text())
    $("#pub").prop("checked", $temp.find("td[name='public']").text() == "Public")
    var i = 1;
    $temp.children(".tag").children().each(function (e) {
        $("#tags" + i).val($(this).text())
        $("#tagadd" + i).click()
        i++

    })
    $("#tagdel" + i).click()

}

function editSubmit() {
    $.ajax({
        type: "POST",
        url: "/editfile",
        data: $("#newfileform").serialize(),
        success: function (result) {
            $("#fileform").html(result)
            reBind()
            refreshtaglist();
            loadTags();
            loadAuth()
        }
    })
}

$("#upfileform").submit(function (e) {
    return false;
})

$("#upfile").on("change", function (e) {
    var fileSize = 0;
    var file = $('#upfile').get(0).files[0];
    if (file.size > 1024 * 1024) {
        fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
    } else {
        fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
    }
    var arr = $('#upfile').val().split("\\");
    var fileName = arr[arr.length - 1];
    $("#upfilename").text(fileName)
    $("#upfilesize").text(fileSize);
})

function uploadFile() {
    var file = $('#upfile').get(0).files;
    //console.log(file)
    var formData = new FormData();
    // for (var i = 0; i < pic.length; i++) {
    //     formData.append("pic['+i+']", pic[i]);
    // }
    if ($("#upispublic").prop("checked")) {
        formData.append("ispublic", "Public");
    } else {
        formData.append("ispublic", "Private");
    }
    formData.append("file", file[0]);



    $.ajax({
        type: "POST",
        url: '/upload',
        data: formData,
        processData: false,
        contentType: false,
        // xhr: function () {
        //     var xhr = $.ajaxSettings.xhr();
        //     if (onprogress && xhr.upload) {
        //         xhr.upload.addEventListener("progress", onprogress, false);
        //         return xhr;
        //     }
        // },
        success: function (e) {
            $("#fileform").html(e)
            reBind()
            loadTags()
            loadAuth()
            $("#upfileform")[0].reset()
            $("#upfilename").text("")
            $("#upfilesize").text("");

        }
    });
}

$("#fileform").submit(function (e) {
    $.ajax({
        type: "POST",
        url: "/delfile/",
        data: $(this).serialize(),
        success: function (result) {
            $("#fileform").html(result)
            reBind()
            refreshtaglist();
        }
    })
    $("#checkAll").prop('checked', false);
    return false
})


$("#newfileform").submit(function (e) {

    $.ajax({
        type: "POST",
        url: "/addfile",
        data: $("#newfileform").serialize(),
        success: function (result) {
            $("#fileform").html(result)
            reBind()
            refreshtaglist();
            loadTags();
            loadAuth()

            $("#newfileform")[0].reset()
            resetAdd()
            $("#btnadd").attr("disabled", "")
        }
    })

    return false
})

i = 1
$.checktag = function (t) {
    if ($("#tags" + t).val() != "" && t < 5) {
        $("#tagadd" + t).removeAttr("disabled")
    } else {
        $("#tagadd" + t).attr("disabled", "")

    }
    checkVoid()
}

function checkVoid() {
    if ($("#tags0").val() != "" && $("#tags" + i).val() != "") {
        $("#btnadd").removeAttr("disabled")

    } else {

        $("#btnadd").attr("disabled", "")
    }
}



$.addtags = function () {
    if (i < 5) {
        i++
        $tags = $("#tag-group").clone();
        $tags.removeAttr("id")
        $tags.attr("id", "tag" + i);
        $tags.find("input").val("").removeAttr("readonly");
        $tags.find("input").attr("id", "tags" + i);
        $tags.find("input").attr("name", "tag" + i);
        $tags.find("input").attr("onkeyup", "$.checktag(" + i + ")");
        $tags.find("button.btnadd").attr("id", "tagadd" + i).attr("disabled", "");
        $tags.find("button.btndel").attr("id", "tagdel" + i).removeAttr("disabled");

        $('#newfileform').append($tags)
        $("#tags" + (i - 1)).attr("readonly", "")
        $("#tagdel" + (i - 1)).attr("disabled", "")
        $("#tagadd" + (i - 1)).attr("disabled", "")
        $("#btnadd").attr("disabled", "")
    }
    if (i == 5) {
        $("#tagadd" + 5).attr("disabled", "")
    }
}

$.deltags = function () {
    if (i == 1) {
        return
    }
    $("#tag" + i).remove()
    $("#tags" + (i - 1)).removeAttr("readonly")
    if (i > 2) {
        $("#tagdel" + (i - 1)).removeAttr("disabled")
    }
    $("#tagadd" + (i - 1)).removeAttr("disabled")
    i--
    $.checktag(i)
}

function resetAdd() {
    while (i > 1) {
        $.deltags()
    }
    $("#tagadd1").attr("disabled", "")
}

function changePage(op){
    if(op==1){
        url="/pageadd"
    }else{
        url="/pageminus"
    }
    $.ajax({
        type: "POST",
        url: url,
        data: '',
        success: function (result) {
            switch_tag($("#allinfo").val(),$("#allinfo").attr("class"),1)
        }
    })
}

