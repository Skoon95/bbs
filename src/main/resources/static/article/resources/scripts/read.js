const commentForm = document.getElementById('commentForm');
const commentContainer = document.getElementById('commentContainer');

function refreshComment(){
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `/article/comment?articleIndex=${commentForm['articleIndex'].value}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const comments = JSON.parse(xhr.responseText);
                for (const comment of comments){
                    const tr = document.createElement('tr');
                    const td = document.createElement('td');
                    const headDiv = document.createElement('div'); //날짜
                    const bodyDiv = document.createElement('div'); // 내용

                    const dtDate = comment['createdAt'].split('T')[0]; //2023-05-26
                    const dtTime = comment['createdAt'].split('T')[1].split('.')[0]; // 02:31:32
                    headDiv.innerText = `${dtDate} ${dtTime}`;
                    bodyDiv.innerText = comment['content'];
                    td.append(headDiv,bodyDiv); //td 의 자식으로 두 개의 인자를 넣음
                    tr.append(td); // tr의 자식으로 td를 가짐
                    commentContainer.append(tr); //가상의 tr를 만듬
                }
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