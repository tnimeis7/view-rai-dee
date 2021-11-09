// const tagContainer = document.querySelector('.tag-container');
// const input = document.querySelector('.tag-container input');
//
// var tags = [] ;
//
// function createTag(label) {
//   const div = document.createElement('div');
//   div.setAttribute('class','tag');
//   const span = document.createElement('span');
//   span.innerHTML = label;
//   const closeBtn = document.createElement('i');
//   closeBtn.setAttribute('class','material-icons');
//   closeBtn.setAttribute('data-item', label);
//   closeBtn.innerHTML = 'close';
//
//   div.appendChild(span);
//   div.appendChild(closeBtn);
//   return div;
// }
//
// function reset(){
//   document.querySelectorAll('.tag').forEach(function (tag) {
//     tag.parentElement.removeChild(tag);
//   });
// }
//
// function addTags(){
//   reset();
//   tags.slice().reverse().forEach(function (tag) {
//     const input = createTag(tag);
//     tagContainer.prepend(input);
//   });
// }
//
// input.addEventListener('keyup', function (e) {
//   if (e.key === 'Enter') {
//     tags.push(input.value);
//     addTags();
//     input.value = '';
//   }
// });
//
// document.addEventListener('click',function (e){
//   if (e.target.tagName === 'I'){
//     const value = e.target.getAttribute('data-item');
//     const index = tags.indexOf(value);
//     tags = [...tags.slice(0, index), ...tags.slice(index+1)];
//     addTags();
//   }
// });

const tagContainer = document.querySelector('.tag-container');
const input = document.querySelector('.tag-container input');

let tags = [];

function createTag(label) {
  const div = document.createElement('div');
  div.setAttribute('class', 'tag');
  const span = document.createElement('span');
  span.innerHTML = label;
  const closeIcon = document.createElement('i');
  closeIcon.innerHTML = 'close';
  closeIcon.setAttribute('class', 'material-icons');
  closeIcon.setAttribute('data-item', label);
  div.appendChild(span);
  div.appendChild(closeIcon);
  return div;
}

function clearTags() {
  document.querySelectorAll('.tag').forEach(tag => {
    tag.parentElement.removeChild(tag);
  });
}

function addTags() {
  clearTags();
  tags.slice().reverse().forEach(tag => {
    tagContainer.prepend(createTag(tag));
  });
}

input.addEventListener('keyup', (e) => {
  if (e.key === 'Enter') {
    e.target.value.split(',').forEach(tag => {
      tags.push(tag);
    });

    addTags();
    input.value = '';
  }
});
document.addEventListener('click', (e) => {
  console.log(e.target.tagName);
  if (e.target.tagName === 'I') {
    const tagLabel = e.target.getAttribute('data-item');
    const index = tags.indexOf(tagLabel);
    tags = [...tags.slice(0, index), ...tags.slice(index+1)];
    addTags();
  }
})

input.focus();