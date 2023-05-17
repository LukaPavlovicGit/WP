window.addEventListener('load', init)

function init(){
    loginMode()
    document.getElementById('login-button').addEventListener('click', userLogin)
    document.getElementById('signup-button').addEventListener('click', userRegister)
    document.getElementById('go-to-login').addEventListener('click', loginMode)
    document.getElementById('go-to-register').addEventListener('click', registerMode)
    document.getElementById('new-post-btn').addEventListener('click', createPostMode)
    document.getElementsByClassName('btn btn-primary post')[0].addEventListener('click', addPost)
    document.getElementsByClassName('btn btn-primary comment')[0].addEventListener('click', addComment)
    document.getElementsByClassName('btn btn-primary back-to-home')[0].addEventListener('click', allPostsMode)
}

function allPostsMode(){
    document.getElementById('sing-in-div').hidden = true
    document.getElementById('sing-up-div').hidden = true
    document.getElementById('all-posts-mode-div-id').hidden = false
    document.getElementById('single-post-mode-div-id').hidden = true
    document.getElementById('create-post-mode-div-id').hidden = true
}

function singlePostMode(){
    document.getElementById('sing-in-div').hidden = true
    document.getElementById('sing-up-div').hidden = true
    document.getElementById('all-posts-mode-div-id').hidden = true
    document.getElementById('single-post-mode-div-id').hidden = false
    document.getElementById('create-post-mode-div-id').hidden = true
}

function createPostMode(){
    document.getElementById('sing-in-div').hidden = true
    document.getElementById('sing-up-div').hidden = true
    document.getElementById('all-posts-mode-div-id').hidden = true
    document.getElementById('single-post-mode-div-id').hidden = true
    document.getElementById('create-post-mode-div-id').hidden = false
}

function loginMode(){
    document.getElementById('sing-in-div').hidden = false
    document.getElementById('sing-up-div').hidden = true
    document.getElementById('all-posts-mode-div-id').hidden = true
    document.getElementById('single-post-mode-div-id').hidden = true
    document.getElementById('create-post-mode-div-id').hidden = true
}

function registerMode(){
    document.getElementById('sing-in-div').hidden = true
    document.getElementById('sing-up-div').hidden = false
    document.getElementById('all-posts-mode-div-id').hidden = true
    document.getElementById('single-post-mode-div-id').hidden = true
    document.getElementById('create-post-mode-div-id').hidden = true
}

function loadPosts(){
    fetch('/api/posts', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + document.getElementById('jwt-holder').innerText
        }
    })
        .then(posts => posts.json())
        .then(posts => posts.forEach(post => addPostElement(post)))
}

function userLogin(event){
    event.preventDefault()

    const username = document.getElementById('email-login').value
    const password = document.getElementById('password-login').value

    fetch('/api/users/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    })
        .then(response => response.json())
        .then(response => {
            if(response.error){
                alert(response.error)
            } else if (response.jwt){
                onLoginSuccess(response.jwt)
            }
        })
}

function userRegister(event){
    event.preventDefault()

    const firstname = document.getElementById('firstname-register').value
    const lastname = document.getElementById('lastname-register').value
    const username = document.getElementById('username-register').value
    const password = document.getElementById('password-register').value

    fetch('/api/users/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            firstname: firstname,
            lastname: lastname,
            username: username,
            password: password
        })
    })
        .then(response => response.json())
        .then(response => {
            if(response.error){
                alert(response.error)
            } else if (response.message){
                alert(response.message)
                loginMode()
            }
        })
}

function onLoginSuccess(jwt){

    const div = document.getElementById('jwt-div')
    // jwt holder
    const jwtHolder = document.createElement('p')
    const firstnameHolder = document.createElement('p')
    const lastnameHolder = document.createElement('p')

    jwtHolder.setAttribute('id', 'jwt-holder')
    firstnameHolder.setAttribute('id', 'firstname-holder')
    lastnameHolder.setAttribute('id', 'lastname-holder')

    jwtHolder.hidden = true
    firstnameHolder.hidden = true
    lastnameHolder.hidden = true

    div.appendChild(jwtHolder)
    div.appendChild(firstnameHolder)
    div.appendChild(lastnameHolder)

    jwtHolder.innerText = jwt

    const jwtDecoded = parseJwt(jwt)
    firstnameHolder.innerText = jwtDecoded.firstname
    lastnameHolder.innerText = jwtDecoded.lastname

    document.getElementById('new-post-author').value = jwtDecoded.firstname + " " + jwtDecoded.lastname
    document.getElementById('comment-author').value = jwtDecoded.firstname + " " + jwtDecoded.lastname
    allPostsMode()
    loadPosts()
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
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + document.getElementById('jwt-holder').innerText
        }
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
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + document.getElementById('jwt-holder').innerText
        },
        body: JSON.stringify({
            author: author,
            title: title,
            content: content,
            dateLong: Date.now()
        })
    })
        .then(response => response.json())
        .then( post => {
            addPostElement(post)
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
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + document.getElementById('jwt-holder').innerText
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
        singlePostMode()
        addSinglePostElement(post)
    })

    allPostsDiv.appendChild(postWrapperDiv)
}

function resetNewPostForm(){
    document.getElementById('new-post-author').value = document.getElementById('firstname-holder').innerText + " " + document.getElementById('lastname-holder').innerText
    document.getElementById('new-post-title').value = ''
    document.getElementById('new-post-content').value = ''
}

function resetNewCommentForm(){
    document.getElementById('comment-author').value = document.getElementById('firstname-holder').innerText + " " + document.getElementById('lastname-holder').innerText
    document.getElementById('comment-content').value = ''
}

function parseJwt (token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}
