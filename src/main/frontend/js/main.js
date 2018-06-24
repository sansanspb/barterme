window.$ = window.jQuery = window.jquery = require('jquery');
require('malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min')($);
window.WOW = require('wowjs').WOW;
require('owl.carousel');
require('EasyTabs/lib/jquery.easytabs.min');
require('vimeo-froogaloop2/javascript/froogaloop.min');
require('./angularjs/app.js');
API = require('./API.js');

$(document).ready(function () {
    console.log('current version: 1.7');
    $('.hide').removeClass('hide');
    API.Categories.getAll().done(function (result) {
        API.Categories.allCategories = result.data;
    });
    /*API.Blogs.getAll().done(function(result){
        if (result.success){
            $('.common-plate').remove();
            for (var i = 0; i < 6; i++){
                var blog = '<div class="common-plate"><a href="blog-page.html">\n' +
                    '                <div class="img-wrapper"><img src="/image/get/' + result.data[i].photo + '"></div>\n' +
                    '                <div class="text-wrapper">\n' +
                    '                  <div class="overf">\n' +
                    '                    <h3 class="mid-title">' + result.data[i].blogId + '</h3>\n' +
                    '                    <p class="common-text grey">\n' + result.data[i].title +
                    '                    </p>\n' +
                    '                  </div>\n' +
                    '                </div></a></div>';
                $('.blog-plates>.clearfix').before(blog);
            }
        }
    });*/

    /*API.Categories.getAll().done(function(result){
        if (result.success){
            var resultTree = [],
                mappedArr = {},
                inputArr = result.data;

            for (var i = 0; i < inputArr.length; i++){
                mappedArr[inputArr[i].categoryId] = inputArr[i];
                mappedArr[inputArr[i].categoryId]['childs'] = [];
            }

            for (var categoryId in mappedArr){
                if (mappedArr[categoryId].parentId != null){
                    mappedArr[mappedArr[categoryId].parentId]['childs'].push(mappedArr[categoryId]);
                } else {
                    resultTree.push(mappedArr[categoryId]);
                }
            }
            $('.one-categ-plate').remove();
            for (var i = 0; i < resultTree.length; i++) {
                var content = '<a class="one-categ-plate" href="listing/' + resultTree[i].categoryId + '"><img src="img/main-page/main_ic_' + resultTree[i].categoryId + '.svg">\n' +
                              '<p class="plate-title common-text">' + resultTree[i].title + '</p>\n';
                var subcontent = '';
                for (var j = 0; j < resultTree[i].childs.length; j++){
                    subcontent += resultTree[i].childs[j].title;
                    if (j < 2){
                        subcontent += ', ';
                    } else {
                        break;
                    }
                }
                content += '<p class="plate-subcategs">' + subcontent + '</p></a>';
                $(".categ-plates").append(content);
            }
        }
    });*/

    $('html, body, .block').click(function () {
        $('.header-right-part .search-wrapper').removeClass('opened');
        $('.search-result-block').fadeOut(200);
        $('.popup').fadeOut(200);
        $('.notif-wrapper').fadeOut(200);
        $('.chosen-sort').removeClass('opened');
        $('.sort-variants').fadeOut(200);
        $('.search-wrapper').removeClass('error');
    })

    const bodyScrollLock = require('body-scroll-lock');
    const disableBodyScroll = bodyScrollLock.disableBodyScroll;
    const enableBodyScroll = bodyScrollLock.enableBodyScroll;


    const targetElement = document.querySelector(".popup");
    const targetElementMenu = document.querySelector(".mob-menu");
    const targetElementCity = document.querySelector("#selectCityPopup");

    $('.hamburger').click(function () {
        $(this).toggleClass('opened');
        $('.mob-menu').toggleClass('opened');
        var clicks = $(this).data('clicks');
        if (clicks) {
            enableBodyScroll(targetElementMenu);
        }
        else {
            disableBodyScroll(targetElementMenu);
        }
        $(this).data("clicks", !clicks);
    });

    new WOW().init();

    $(window).scroll(function () {

        var y = $(this).scrollTop();
        if (y > 10) {
            $('.page-header').addClass('ontop');
        }
        else {
            $('.page-header').removeClass('ontop');
        }
    });

    // Smooth footer anchors

    $('.footer-col').on('click', '.smooth-anchor', function (event) {
        event.preventDefault()
        let id = $(this).attr('href'),
            top = $(id).offset().top

        $('body,html').animate({scrollTop: top}, 1500)
    })

    // Requests

    $('.decline').click(function (declineRequest) {
        declineRequest.preventDefault();
        $(this).closest('.one-request').find('.request-btns').fadeOut(100);
        $(this).closest('.one-request').find('.request-remove-alert').delay(300).fadeIn(300);
    })

    $('.yesremove').click(function (removeRequest) {
        removeRequest.preventDefault();
        $(this).closest('.one-request').fadeOut(200);
    })
    $('.noremove').click(function (returnRequest) {
        returnRequest.preventDefault();
        $(this).closest('.one-request').find('.request-btns').delay(300).fadeIn(300);
        $(this).closest('.one-request').find('.request-remove-alert').fadeOut(100);
    })

    // Эта механика тоже кривоватая, надо будет сделать нормально

    $('.one-request:last-of-type .yesremove').click(function () {
        $('.norequests').delay(200).fadeIn(300);
    })

    // Favourite

    /*$('.add-to-favs').click(function () {
        $(this).toggleClass('pressed');
    })*/

    // Chat

    $('.close-chat').click(function () {
        $('.chat-wrapper').addClass('hidden');
    })

    // Cabinet

    $('.ok').click(function () {
        $(this).closest('.one-time-notif').fadeOut(300);
    })

    $('.cabinet-tab-container, .partner-tabs').easytabs();

    $('.remove-fav').click(function (removeFav) {
        removeFav.preventDefault();
        $(this).closest('.common-plate').fadeOut(300);
    });

    $('.common-plate.inside-categ-plate.cab-fav-plate:last-of-type .remove-fav').click(function () {
        $('.nofav-block').delay(200).fadeIn(300);
    })

    $('.editcity').click(function (editCity) {
        editCity.preventDefault();
        editCity.stopPropagation();
        $('#selectCityPopupWrapper').fadeIn(300);
        $('#selectCityPopup').fadeIn(300);
    })

    $('.finish-partner').click(function (finishPartner) {
        finishPartner.preventDefault();
        finishPartner.stopPropagation();
        $('#finishPartnerWrapper').fadeIn(300);
        $('#finishPartnerPopup').fadeIn(300);
    })

    $("#closePartnership").click(function (closeP) {
        closeP.preventDefault();
        $('#finishPartnerWrapper').fadeOut(300);
        $('#finishPartnerPopup').fadeOut(300);
        $(".tofinish").fadeOut(100);
        $(".nopartners").fadeIn(400);
    })

    $('.editgeo').click(function (editGeo) {
        editGeo.preventDefault();
        editGeo.stopPropagation();
        $('#selectGeoPopupWrapper').fadeIn(300);
        $('#selectGeoPopup').fadeIn(300);
    })

    $('.city-name').click(function () {
        $('.popup-wrapper, .popup').fadeOut(200);
    })

    $('#removePic').click(function (deleteAva) {
        deleteAva.preventDefault();
        $('.img-wrapper.cab').addClass('empty');
    })

    $('.delete-ava').click(function (deleteAva) {
        deleteAva.preventDefault();
        deleteAva.stopPropagation();
        $('#deleteUserPicWrapper').fadeIn(300);
        $('#deleteUserPicPopup').fadeIn(300);
    })

    $('#removePic').click(function (deleteAva) {
        deleteAva.preventDefault();
        $('.img-wrapper.cab').addClass('empty');
        $('#deleteUserPicWrapper').fadeOut(300);
        $('#deleteUserPicPopup').fadeOut(300);
    })

    $('#notRemovePic').click(function (deleteAva) {
        deleteAva.preventDefault();
        $('#deleteUserPicWrapper').fadeOut(300);
        $('#deleteUserPicPopup').fadeOut(300);
    })

    $('.openable span').click(function () {
        $(this).parent('.openable').find('.card-vars-list').fadeToggle(200);
    })

    // Slider


    $('.c-slider').owlCarousel({

        loop: true,
        items: 4,
        navigation: true,
        pagination: false,
        responsiveClass: true,
        responsive: {
            0: {
                items: 4
            },
            400: {
                items: 4
            }
        }
    })

    $('.card-slider').owlCarousel({
        loop: true,
        items: 1,
        singleItem: true,
        navigation: false,
        pagination: true,
        autoplay: true,
        responsiveClass: false
    })

    // Header search

    $('.header-search-btn').click(function (hSearch) {
        hSearch.stopPropagation();
        API.Auth.getInfo()
            .then(
                function (result) {
                    if (result.success) {
                        for (var i = 0; i < result.data.roles.length; i++) {
                            if (result.data.roles[i] == 'ROLE_CONFIRMED_USER') {
                                API.Auth.isRegistered = true;
                                API.Companies.getOthersCompanies().done(function (result) {
                                    API.Companies.otherCompanies = result.data;
                                });
                            }
                        }
                    } else {
                        API.Auth.isRegistered = false;
                    }

                }
            )
        $('.notif-wrapper').fadeOut(200);
        $('.header-right-part .search-wrapper').toggleClass('opened');
        $('#headerSearch').focus();
    })
    $('#mainSearch').click(function () {
        API.Auth.getInfo()
            .then(
                function (result) {
                    if (result.success) {
                        for (var i = 0; i < result.data.roles.length; i++) {
                            if (result.data.roles[i] == 'ROLE_CONFIRMED_USER') {
                                API.Auth.isRegistered = true;
                                API.Companies.getOthersCompanies().done(function (result) {
                                    API.Companies.otherCompanies = result.data;
                                });
                            }
                        }
                    } else {
                        API.Auth.isRegistered = false;
                    }

                }
            )
    });

    $('.start-search').click(function (searchError) {
        searchError.preventDefault();
        searchError.stopPropagation();
        $(this).closest('.search-wrapper').addClass('error');
    })

    $('.header-right-part .search-wrapper').click(function (ss) {
        ss.stopPropagation();
    })

    $('.start-search').click(function (stS) {
        stS.preventDefault();
    })

    // Поменяйте эти условия при дальнейшей разработке
    /*$('#headerSearch').click(function (e) {
        $( "#headerSearch" ).keyup();
    });*/
    $("#headerSearch").on('keyup', function (e) {

        $('#headerSearchResults>.s-r-list>.s-r-item').remove();
        var categs = API.Categories.allCategories,
            resultTags = '',
            companies = API.Companies.otherCompanies;
        if (API.Auth.isRegistered) {
            for (var i = 0; i < companies.length; i++) {
                if (companies[i].caption.toUpperCase().indexOf($("#headerSearch").val().toUpperCase()) != -1) {
                    resultTags += '<li class="s-r-item"><a class="s-r-link" href="card?parentId=1&subId=9&companyId=' + companies[i].companyId + '">' + companies[i].caption + '</a></li>';
                }
            }
        }
        for (var i = 0; i < categs.length; i++) {
            if (categs[i].title.toUpperCase().indexOf($("#headerSearch").val().toUpperCase()) != -1) {
                if (API.Auth.isRegistered) {
                    if (categs[i].parentId == null) {
                        resultTags += '<li class="s-r-item"><a class="s-r-link" href="listing?id=' + categs[i].categoryId + '">' + categs[i].title + '</a></li>';
                    } else {
                        resultTags += '<li class="s-r-item"><a class="s-r-link" href="insideCateg?parentId=' + categs[i].parentId +
                            '&subId=' + categs[i].categoryId + '">' + categs[i].title + '</a></li>';
                    }
                } else {
                    if (categs[i].parentId == null) {
                        resultTags += '<li class="s-r-item"><a class="s-r-link" href="listing?id=' + categs[i].categoryId + '">' + categs[i].title + '</a></li>';
                    }
                }
            }
        }
        if (resultTags == '') {
            $('#headerSearchResults>.s-r-list').append('<li class="s-r-item"><a class="s-r-link" href="#">Ничего не найдено</a></li>');
        } else {
            $('#headerSearchResults>.s-r-list').append(resultTags);
        }

        $('#headerSearchResults').fadeIn(300);
        $('.search-wrapper').removeClass('error');
    });
    /*$('#mainSearch').click(function (e) {
        $( "#mainSearch" ).keyup();
    });*/
    $("#mainSearch").on('keyup', function (e) {
        $('#mainSearchResults>.s-r-list>.s-r-item').remove();
        var categs = API.Categories.allCategories,
            resultTags = '',
            companies = API.Companies.otherCompanies;
        if (API.Auth.isRegistered) {
            for (var i = 0; i < companies.length; i++) {
                if (companies[i].caption.toUpperCase().indexOf($("#mainSearch").val().toUpperCase()) != -1) {
                    resultTags += '<li class="s-r-item"><a class="s-r-link" href="card?parentId=1&subId=9&companyId=' + companies[i].companyId + '">' + companies[i].caption + '</a></li>';
                }
            }
        }
        for (var i = 0; i < categs.length; i++) {
            if (categs[i].title.toUpperCase().indexOf($("#mainSearch").val().toUpperCase()) != -1) {
                if (API.Auth.isRegistered) {
                    if (categs[i].parentId == null) {
                        resultTags += '<li class="s-r-item"><a class="s-r-link" href="listing?id=' + categs[i].categoryId + '">' + categs[i].title + '</a></li>';
                    } else {
                        resultTags += '<li class="s-r-item"><a class="s-r-link" href="insideCateg?parentId=' + categs[i].parentId +
                            '&subId=' + categs[i].categoryId + '">' + categs[i].title + '</a></li>';
                    }
                } else {
                    if (categs[i].parentId == null) {
                        resultTags += '<li class="s-r-item"><a class="s-r-link" href="listing?id=' + categs[i].categoryId + '">' + categs[i].title + '</a></li>';
                    }
                }
            }
        }
        if (resultTags == '') {
            $('#mainSearchResults>.s-r-list').append('<li class="s-r-item"><a class="s-r-link" href="#">Ничего не найдено</a></li>');
        } else {
            $('#mainSearchResults>.s-r-list').append(resultTags);
        }
        $('#mainSearchResults').fadeIn(300);
        $('.search-wrapper').removeClass('error');
    });

    // Notifications

    $('.bell-block').click(function (notifs) {
        notifs.stopPropagation();
        $('.notif-wrapper').fadeIn(300);
    })

    //Sort

    $('#openSort').click(function (openSort) {
        openSort.stopPropagation();
        $('.chosen-sort').toggleClass('opened');
        $('.sort-variants').fadeToggle(300);
    })

    // Show more feedbacks

    $('#showMoreFeedbacks').click(function (showMoreFeedbacks) {
        showMoreFeedbacks.preventDefault();

        var text = $(this).text();
        $(this).text(
            text == "Показать больше отзывов" ? "Скрыть отзывы" : "Показать больше отзывов");
        $('.hidden-feedbacks').fadeToggle();
    })


    // Open Order Form

    $('.submit-request').on('click', function () {
        var id = $(this).attr('href'),
            top = $(id).offset().top - 55;
        $('body,html').animate({scrollTop: top}, 1500);
        $('.order-teaser-block').addClass('shrinked');
        $('.order-block').removeClass('shrinked');
    });

    $('.close-order-block').on('click', function () {
        $('.order-teaser-block').removeClass('shrinked');
        $('.order-block').addClass('shrinked');
    });


    //select all checkboxes

    $("#selectAll").change(function () {  //"select all" change
        var status = this.checked; // "select all" checked status
        $('.checkbox.sall').each(function () { //iterate all listed checkbox items
            this.click(); //change ".checkbox" checked status
        });
    });

    $('.checkbox.sall').change(function () { //".checkbox" change
        //uncheck "select all", if one of the listed checkbox item is unchecked
        if (this.checked == false) { //if this item is unchecked
            $("#selectAll")[0].checked = false; //change "select all" checked status to false
        }

        //check "select all" if all checkbox items are checked
        if ($('.checkbox.sall:checked').length == $('.checkbox').length) {
            $("#selectAll")[0].checked = true; //change "select all" checked status to true
        }
    });


    // Popups

    $('.regBtn').click(function (callRegPop) {
        callRegPop.preventDefault();
        callRegPop.stopPropagation();
        $('#regPopupWrapper').fadeIn(300);
        $('#regPopup').fadeIn(300);
    })

    $('#entranceBtn').click(function (callEntrancePop) {
        callEntrancePop.preventDefault();
        callEntrancePop.stopPropagation();
        $('#regPopupWrapper').fadeIn(300);
        $('#entrancePopup').fadeIn(300);
    })

    $('#goToReg').click(function (goToReg) {
        goToReg.preventDefault();
        $('#entrancePopup').fadeOut(200);
        $('#regPopup').fadeIn(300);
        $('.mob-menu').removeClass('opened');
    })

    $('#goToEntrance').click(function (goToEnt) {
        goToEnt.preventDefault();
        $('#regPopup').fadeOut(200);
        $('#entrancePopup').fadeIn(300);
        $('.mob-menu').removeClass('opened');
    })

    $('#login').click(function (toCode) {
        toCode.preventDefault();
        toCode.stopPropagation();
        var $form = $(this.form),
            email = $form.find('input[name="email"]').val(),
            password = $form.find('input[name="password"]').val();
        console.log(email);
        API.Auth.Login(email, password).done(function (result) {
            if (result.success) {
                document.location.href = 'cabinet';
            }
        });
    });

    $('a.subcateg-card').click(function (event) {
        event.preventDefault();
        var a = $(this);
        API.Auth.getInfo().done(function (result) {
            if (result.success) {
                window.location.href = a.attr('href');
            } else {
                $('#regPopupWrapper').fadeIn(300);
                $('#entrancePopup').fadeIn(300);
            }
        });
    });

    $('#regist').click(function (toCode) {
        toCode.preventDefault();
        toCode.stopPropagation();
        var $form = $(this.form),
            title = $form.find('input[name="comName"]').val(),
            email = $form.find('input[name="corpMail"]').val(),
            phone = $form.find('input[name="regPhone"]').val(),
            password = $form.find('input[name="regPass"]').val();
        API.Auth.Register(title, email, phone, password).done(function (result) {
            if (result.success) {
                $('#regPopup').fadeOut(200);
                $('#finishregPopup').fadeIn(300);
            }
        });
    });

    $('#confirmBtn').click(function (confirm) {
        confirm.preventDefault();
        confirm.stopPropagation();
        $('#regPopupWrapper').fadeIn(300);
        $('#codePopup').fadeIn(300);
    })

    $('#finishReg').click(function (finishReg) {
        finishReg.preventDefault();
        finishReg.stopPropagation();
        var $form = $(this.form),
            confirmToken = $form.find('input[name="code"]').val();
        API.Auth.Confirm(confirmToken).done(function (result) {
            if (result.success) {
                $('#codePopup').fadeOut(200);
                $('#moderPopup').fadeIn(300);
            }
        });
    })

    $('#exitReg').click(function (exitReg) {
        exitReg.preventDefault();
        $('.popup-wrapper').fadeOut(200);
        $('.popup').fadeOut(200);
        $('.mob-menu, .hamburger').removeClass('opened');
        window.location.href = "cabinet";
    })

    $('#goToFinish').click(function (goToFinish) {
        goToFinish.preventDefault();
        $('#moderPopup').fadeOut(200);
        $('#finishregPopup').fadeIn(300);
    })

    $('.popup-wrapper, .close-popup').click(function () {
        $('.popup-wrapper').fadeOut(200);
        $('.popup').fadeOut(200);
        $('.mob-menu').removeClass('opened');
        $('.show-error').removeClass('error');
        $('.error-message').fadeOut(200);
        $('.mob-menu, .hamburger').removeClass('opened');
        angular.element($('.page-header')).scope().hctrl.loadHead();
    })


    $('.popup').click(function (pop) {
        pop.stopPropagation();
    })

    $('#seeError').click(function (seeError) {
        seeError.preventDefault();
        $('.show-error').addClass('error');
        $('.error-message').fadeIn(200);
    })

    $('#okWait, #submitRequestPopupWrapper, #closeRequest').click(function (okWait) {
        okWait.preventDefault();
        $('.popup-wrapper').fadeOut(200);
        $('.order-teaser-block').removeClass('shrinked');
        $('.order-block').addClass('shrinked');
    })

    $('#goPartner').click(function (goPartner) {
        goPartner.stopPropagation();
        $('#partnerPopupWrapper').fadeIn(300);
        $('#partnerPopup').fadeIn(300);
    })

    $('#cool').click(function (okWait) {
        okWait.preventDefault();
        $('.popup-wrapper').fadeOut(200);
    })

    $('.card-showmore').click(function (showMore) {
        showMore.preventDefault();
        showMore.stopPropagation();
        $('#showMorePopupWrapper').fadeIn(300);
        $('#showMorePopup').fadeIn(300);
    })

    // Video

    $('#play-button').click(function (stVid) {
        stVid.preventDefault();
        $('.ipad').addClass('moved');
        $('.video-info-block').addClass('hidden');
        $('.video-block').delay(500).fadeIn(300);
    })


    var iframe = document.getElementById('video');
    var player = $f(iframe);
    var playButton = document.getElementById("play-button");
    if (playButton) {
        playButton.addEventListener("click", function () {
            player.api("play");
        });
    }


    $('#play-button-mob').click(function (mobVid) {
        mobVid.preventDefault();
        $('.mob-video-block').fadeIn(300);
    })

    $('.pagination-arrow-next, .paginatin-circle').click(function (pag) {
        pag.preventDefault();
    })

    $('.paginatin-circle').click(function () {
        $('.paginatin-circle').removeClass('active');
        $(this).addClass('active');
    });

    if ($(window).width() < 705) {
        $('.editcity, .regBtn').click(function () {
            disableBodyScroll(targetElement);
        })
    }

});

document.addEventListener("touchstart", function () {
}, true);