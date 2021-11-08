// function showContent(selector, maxLength) {
//     var element = document.querySelector(selector),
//         truncated = element.innerText;
//     if (truncated.length > maxLength) {
//         truncated = truncated.substr(0, maxLength) + "...";
//     }
//     return truncated;
// }
//
// document.querySelector('p').innerText = showContent('p', 3);

$(function () {
    $("#carousel-multiple").on("slide.bs.carousel", function (e) {
        var itemsPerSlide = parseInt($(this).attr('data-maximum-items-per-slide')),
            totalItems = $(".carousel-item", this).length,
            reserve = 1,//do not change
            $itemsContainer = $(".carousel-inner", this),
            it = (itemsPerSlide + reserve) - (totalItems - e.to);

        if (it > 0) {
            for (var i = 0; i < it; i++) {
                $(".carousel-item", this)
                    .eq(e.direction == "left" ? i : 0)
                    // append slides to the end/beginning
                    .appendTo($itemsContainer);
            }
        }
    });
});