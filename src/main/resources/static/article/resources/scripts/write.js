const writeForm = document.getElementById('writeForm');

ClassicEditor.create(writeForm['content']);


// writeForm.onsubmit = e =>{
//   e.preventDefault();
//   const xhr = new XMLHttpRequest();
//   const formData = new FormData();
//   formData.append('title',writeForm['vtitle'].value);
//   formData.append('content',writeForm['content'].value);
//     for (const file of writeForm['files'].files){
//         formData.append('files',file);
//     }
//   xhr.open('POST','/article/write');
//   xhr.onreadystatechange = () => {
//       if (xhr.readyState === XMLHttpRequest.DONE) {
//           console.log(xhr.responseText);
//           if (xhr.status >= 200 && xhr.status < 300) {
//               console.log('성공');
//               // if (xhr.responseText === 'true'){
//               //     alert('업로드 성공');
//               // } else {
//               //     alert('업로드 실패');
//               //
//               // }
//           } else {
//               alert('알 수 없는 이유로 서버와 통신하지 못했습니다.');
//           }
//       }
//   };
//   xhr.send(formData);
// };