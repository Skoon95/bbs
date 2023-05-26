const commentForm = document.getElementById('commentForm');
const commentContainer = document.getElementById('commentContainer');

function refreshComment(){
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `/article/comment?articleIndex=${commentForm['articleIndex'].value}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const comments = JSON.parse(xhr.responseText);
            } else {
                alert('서바와 통신하지 못하였습니다. 잠시 후 다시 시도해주세요');
            }
        }
    };
    xhr.send();
}

commentForm.onsubmit = function (e) {
    e.preventDefault();

    if (commentForm['content'].value===''){
        alert('댓글을 입력해 주세요.');
        commentForm['content'].focus();
        return;
    }
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('articleIndex',commentForm['articleIndex'].value);
    formData.append('content',commentForm['content'].value);
    xhr.open('POST', '/article/comment');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                if (xhr.responseText === 'true'){
                    commentForm['content'].value='';
                    commentForm['content'].focus();
                }else {
                    alert('댓글을 작성하지 못하였습니다. 잠시 후 다시 시도해 주세요');
                }
            } else {
                alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요');
            }
        }
    };
    xhr.send(formData);
};

document.getElementById('refreshComment').onclick=refreshComment;