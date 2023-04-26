window.addEventListener('load', init)

function init(){
    allPostsMode()
    document.getElementById('new-post-btn').addEventListener('click', createPostMode)
    document.getElementsByClassName('btn btn-primary post')[0].addEventListener('click', addPost)
    document.getElementsByClassName('btn btn-primary comment')[0].addEventListener('click', addComment)
}

function allPostsMode(){
    document.getElementById('all-posts-mode-div-id').hidden = false
    document.getElementById('single-post-mode-div-id').hidden = true
    document.getElementById('create-post-mode-div-id').hidden = true
    loadPosts()
}

function singlePostMode(post){
    document.getElementById('all-posts-mode-div-id').hidden = true
    document.getElementById('single-post-mode-div-id').hidden = false
    document.getElementById('create-post-mode-div-id').hidden = true
    addSinglePostElement(post)
}

function createPostMode(){
    document.getElementById('all-posts-mode-div-id').hidden = true
    document.getElementById('single-post-mode-div-id').hidden = true
    document.getElementById('create-post-mode-div-id').hidden = false
}

function addSinglePostElement(post){

    const singlePostDiv = document.getElementById('single-post-div-id')

    const postHeader = document.createElement("h1")
    const postDate = document.createElement('p')
    const postAuthor = document.createElement('p')
    const postContent = document.createElement('p')
    // use it to know postId and set it to be hidden
    const postId = document.createElement('p')
    postId.setAttribute('id', 'post-id-holder')
    postId.hidden = true
    const commentsHeader = document.createElement("h3")

    postHeader.innerText = post.title
    postDate.innerText = new Date(post.dateLong).toLocaleDateString()
    postAuthor.innerText = post.author
    postContent.innerText = post.content
    postId.innerText = post.id
    commentsHeader.innerText = "Comments"

    singlePostDiv.appendChild(postHeader)
    singlePostDiv.appendChild(postDate)
    singlePostDiv.appendChild(postAuthor)
    singlePostDiv.appendChild(postContent)
    singlePostDiv.appendChild(postId)
    singlePostDiv.appendChild(document.createElement("br"))
    singlePostDiv.appendChild(commentsHeader)

    fetch(`/api/comments/byPostId/${post.id}`, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(comments => comments.forEach(comment => addCommentElement(comment)))
}

function addCommentElement(comment){
    const singlePostCommentsDiv = document.getElementById('single-post-comments-div-id')

    const commentAuthor = document.createElement('p')
    const commentContent = document.createElement('p')

    commentAuthor.innerText = comment.author
    commentContent.innerText = comment.content

    singlePostCommentsDiv.appendChild(commentAuthor)
    singlePostCommentsDiv.appendChild(commentContent)
    singlePostCommentsDiv.appendChild(document.createElement("br"))
}

function addPost(event){
    event.preventDefault()

    const author = document.getElementById('new-post-author').value
    const title = document.getElementById('new-post-title').value
    const content = document.getElementById('new-post-content').value

    fetch('/api/posts', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            author: author,
            title: title,
            content: content,
            dateLong: Date.now()
        })
    }).then( post => {
        allPostsMode()
        resetNewPostForm()
    })
}

function addComment(event){
    event.preventDefault()

    const postId = document.getElementById('post-id-holder').innerText
    const author = document.getElementById('comment-author').value
    const content = document.getElementById('comment-content').value

    fetch(`/api/comments`,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            author: author,
            content: content,
            postId: postId
        })
    })
        .then(response => response.json())
        .then(comment => {
            addCommentElement(comment)
            resetNewCommentForm()
        })
}

function loadPosts(){
    fetch('/api/posts', {
        method: 'GET'
    })
        .then(posts => posts.json())
        .then(posts => posts.forEach(post => addPostElement(post)))
}

function addPostElement(post){
    const allPostsDiv = document.getElementById('posts-div-id')

    const postWrapperDiv = document.createElement('div')
    const postHeader = document.createElement('h2')
    const postParagraph = document.createElement('p')

    postHeader.innerText = post.title
    postParagraph.innerText = post.content

    postWrapperDiv.appendChild(postHeader)
    postWrapperDiv.appendChild(postParagraph)
    postWrapperDiv.appendChild(document.createElement('br'))
    postWrapperDiv.addEventListener('click', function() {
        singlePostMode(post)
    })

    allPostsDiv.appendChild(postWrapperDiv)
}

function resetNewPostForm(){
    document.getElementById('new-post-author').value = ''
    document.getElementById('new-post-title').value = ''
    document.getElementById('new-post-content').value = ''
}

function resetNewCommentForm(){
    document.getElementById('comment-author').value = ''
    document.getElementById('comment-content').value = ''
}

