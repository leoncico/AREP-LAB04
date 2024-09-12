document.getElementById('messageForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const messageInput = document.getElementById('messageInput');
    const message = messageInput.value;

    fetch(`/log/response?value=${encodeURIComponent(message)}`, {
        method: 'GET'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la respuesta del servidor');
        }
        return response.json();
    })
    .then(data => {
        updateLogTable(data);
        messageInput.value = '';
    })
    .catch(error => {
        console.error('Error:', error);
    });
});

function updateLogTable(logs) {
    const tableBody = document.getElementById('messageTableBody');
    tableBody.innerHTML = '';
    logs.forEach(log => {
        const row = document.createElement('tr');
        const messageCell = document.createElement('td');
        messageCell.textContent = log.value;
        row.appendChild(messageCell);
        const dateCell = document.createElement('td');
        dateCell.textContent = log.date;
        row.appendChild(dateCell);
        tableBody.appendChild(row);
    });
}
