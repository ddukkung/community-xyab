'use strict';

let index = {
    init: function () {
        $("#btn-save").on("click", () => {
            let form = document.querySelector("#needs-validation");
            if (form.checkValidity() == false) {
                console.log("회원가입 실패")
            } else {
                this.save();
            }
        });
        $("#btn-update").on("click", () => {
            let form = document.querySelector("#needs-validation");
            if (form.checkValidity() == false) {
                console.log("회원 정보 수정 실패")
            } else {
                this.update();
            }
        });
        $("#btn-userDelete").on("click", () => {
            this.userDelete();
        });
    },

    save: function () {
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val(),
            nickname: $("#nickname").val()
        }

        $.ajax({
            type: "POST",
            url: "/auth/api/v1/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res) {
            alert("회원가입이 완료되었습니다.");
            location.href = "/";
        }).fail(function (err) {
            alert(JSON.stringify(err));
        });
    },

    update: function () {
        let data = {
            id: $("#id").val(),
            password: $("#password").val(),
            nickname: $("#nickname").val()
        }

        $.ajax({
            type: "PUT",
            url: "/api/v1/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res) {
            alert("회원 정보 수정이 완료되었습니다.");
            location.href = "/";
        }).fail(function (err) {
            alert(JSON.stringify(err));
        });
    },

    userDelete: function () {
        let data = {
            id: $("#id").val(),
            password: $("#password").val()
        }

        $.ajax({
            type: "DELETE",
            url: "/api/v1/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "text"
        }).done(function (res) {
            alert("회원 탈퇴가 완료되었습니다.");
            location.href = "/";
        }).fail(function (err) {
            alert(JSON.stringify(err));
        });
    }
}
index.init();