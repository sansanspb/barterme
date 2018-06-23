var serverApiUrl = {
    auth : {
        register        :   'auth/register',
        login           :   'auth/login',
        confirmUser     :   'auth/confirmUser',
        getInfo         :   'auth/getUserInfo'
    },
    blogs : {
        getAll          :   'blogs/getAll'
    },
    categories : {
        getAll   :   'categories/getAll',
        save            :   'company/save',
        test : ''
    },
    companies : {
        getOthersCompanies : 'company/getOthersCompanies'
    }
};

module.exports = {
    Auth : {
        Register : function Register(title, email, phone, password){
            var data = JSON.stringify({title: title, email: email, phone: phone, password: password});
                return $.ajax({
                    url: serverApiUrl.auth.register,
                    dataType: 'json',
                    type: 'post',
                    contentType: 'application/json',
                    data: data
                });
        },
        Login: function Login(email, password){
            var data = JSON.stringify({email: email, password: password});
                return $.ajax({
                    url: serverApiUrl.auth.login,
                    dataType: 'json',
                    type: 'post',
                    contentType: 'application/json',
                    data: data
                });
        },
        Confirm : function Confirm(confirmToken){
            var data = {confirmToken: confirmToken};
                return $.ajax({
                    url: serverApiUrl.auth.confirmUser,
                    dataType: 'json',
                    type: 'post',
                    data: data
                });
        },
        getInfo : function(){
            return $.ajax({
                url: serverApiUrl.auth.getInfo,
                dataType: 'json',
                type: 'post',
                data: {}
            });
        }
    },
    Blogs : {
        getAll : function getAll(){
            return $.ajax({
                url: serverApiUrl.blogs.getAll,
                dataType: 'json',
                type: 'post',
                data: {}
            });
        }
    },
    Categories : {
        getAll : function getAll(){
            return $.ajax({
                url: serverApiUrl.categories.getAll,
                dataType: 'json',
                type: 'post',
                data: {}
            });
        }
    },
    Companies : {
        getOthersCompanies : function (){
            return $.ajax({
                url: serverApiUrl.companies.getOthersCompanies,
                dataType: 'json',
                type: 'post',
                data: {}
            });
        }
    }
}