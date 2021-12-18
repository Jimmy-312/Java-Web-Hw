var toolbar;
toolbar = [
    {
        hotkey: '⌘S',
        name: 'Save',
        tipPosition: 'n',
        tip: 'Save',
        // className: 'right',
        icon: '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-save2" viewBox="0 0 16 16">\
        <path d="M2 1a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H9.5a1 1 0 0 0-1 1v4.5h2a.5.5 0 0 1 .354.854l-2.5 2.5a.5.5 0 0 1-.708 0l-2.5-2.5A.5.5 0 0 1 5.5 6.5h2V2a2 2 0 0 1 2-2H14a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h2.5a.5.5 0 0 1 0 1H2z"/>\
      </svg>',
        click () {$("#btnsave").click();return false},
      },
    "outline","|",
    "undo", "redo","|",
    "emoji", "headings","bold", "italic", "strike", "link", "|",
     "outdent" ,"indent", "list", "ordered-list", "check", "|",
    "quote", "line", "code", "inline-code","|",
    "upload", "table", "|",
    "edit-mode",
    {
        name: 'more',
        tipPosition: 'n',
        tip: 'More',
        icon:'<svg><use xlink:href="#vditor-icon-more"></use></svg>',
        className: 'right',
        click () {return false},
        toolbar:["export", "devtools",]
      },



];

vditor = new Vditor("vditor", {
    focus(md) {
        document.onkeydown = function (event) {
            if (event.ctrlKey == true && event.keyCode == 83) {
                $("#btnsave").click();
                event.preventDefault();
            }
        }
    },
    lang: 'en_US',
    cache:{
        enable:false
    },
    toolbar,
    fullscreen: {
        index: 9999,
    },
    mode: "ir",

     height: 700,
    // height: screen.height,
    //height: 500,


    debugger: false,

    typewriterMode: true,

    placeholder: "Enjoy Yourself! :)",

    toolbarConfig: {
        pin: false,
    },

    autoSpace:true,
    counter: {
        enable: true,
    },

    tab: "\t",

    upload: {
        accept: "image/*",
        token: "img",
        url: "/upload/img",
        linkToImgUrl: "/upload/img",
        filename(name) {
            return name
                .replace(/[^(a-zA-Z0-9\u4e00-\u9fa5\.)]/g, "")
                .replace(/[\?\\/:|<>\*\[\]\(\)\$%\{\}@~]/g, "")
                .replace("/\\s/g", "");
        },
    },
    input () {
        autoSave();
      },
      focus(){
          autoSave()
      },
    preview:{
        math:{
            inlineDigit:true,
        }
    }
});
