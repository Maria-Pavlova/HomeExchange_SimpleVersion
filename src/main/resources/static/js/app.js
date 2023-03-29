// let loadMessagesBtn = document.getElementById('loadMessages');
//
// loadMessagesBtn.addEventListener('click', onLoadMessages);
// function onLoadMessages(event){

    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    let messages = document.getElementById('messages')
    messages.innerHTML = ''

    fetch("http://localhost:8080/api/messages", requestOptions)
        .then(response => response.json())
        .then(json => json.forEach(message => {


            let row = document.createElement('tr')

            let senderNameCol = document.createElement('td')
            let senderEmailCol = document.createElement('td')
            let subjectCol = document.createElement('td')
            let textCol = document.createElement('td')
            let actionCol = document.createElement('td')

            senderNameCol.textContent = message.fromUserUsername;
            senderEmailCol.textContent = message.fromUserEmail;
            subjectCol.textContent = message.subject;
            textCol.textContent = message.text;

            row.appendChild(senderNameCol);
            row.appendChild(senderEmailCol);
            row.appendChild(subjectCol);
            row.appendChild(textCol);

            messages.appendChild(row);

        }))
        .catch(error => console.log('error', error));
//}