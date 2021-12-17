var toolbar;
toolbar = [
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
        toolbar:["export", "content-theme", "code-theme", "devtools",]
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

    // height: window.innerHeight,
    // height: screen.height,
    height: 800,


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
