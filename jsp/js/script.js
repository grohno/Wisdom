// Loading
window.onload = function() {
  const spinner = document.getElementById('loading');
  spinner.classList.add('loaded');
}

// resignCheck
function resignCheck() {
    if( confirm("本当に退会手続きをとってよろしいですか？\n（OKを押すと退会しログアウトします）") ) {
    	return true;
    }
    else {
        alert("退会手続きをキャンセルしました");
        return false;
    }
}

// bestAnsCheck
function bestAnsCheck() {
    if( confirm("この回答をベストアンサーにしてよろしいですか？\n（OKを押すと決定します！）") ) {
    	return true;
    }
    else {
        alert("キャンセルしました");
        return false;
    }
}

// helpfulCheck
function helpfulCheck() {
    if( confirm("この回答に助かった！マークをつけますか？\n（OKを押すと決定します！）") ) {
    	return true;
    }
    else {
        alert("キャンセルしました");
        return false;
    }
}

// 投稿文のURL自動リンク
$(function(){
    $('.pre').each(function(){
        $(this).html($(this).html().replace(/(\b(https?|ftp|file):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/ig,"<a target='_blank' href='$1'>$1</a>"));
    });
});