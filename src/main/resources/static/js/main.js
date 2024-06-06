// Get the id from the URL
let url = window.location.pathname;
let id = url.substring(url.lastIndexOf('/') + 1);
let labels = [];
let data = [];

// Establish a SockJS connection
var socket = new SockJS("/ws");

// Create a STOMP client over the SockJS connection
var stompClient = Stomp.over(socket);

// Connect to the server
stompClient.connect({}, function(frame) {
    // Subscribe to the "/topic/prices" topic
    stompClient.subscribe('/topic/prices', function(message) {
        // Parse the message body as JSON
        var data = JSON.parse(message.body);

        // For each key-value pair in the data
        for (var id in data) {
            // Find the corresponding element on the page
            var element = document.getElementById(id);

            // If the element exists
            if (element) {
                // Update its content to show the currency and its value
                element.textContent = `$${data[id]}`;
                element.classList.remove('animate__flash');

                // Initiate update when new data arrives
                sendUpdateRequest(id);
            }
        }
    });

    stompClient.subscribe('/topic/price/' + id, function(priceData) {
        // Parse the price data
        let priceInfo = JSON.parse(priceData.body);

        // Get the current time
        let currentTime = new Date().toLocaleTimeString();

        // Add new data
        labels.push(currentTime);
        data.push(priceInfo);

        // Remove old data
        if(labels.length > 99) {
            labels.shift();
            data.shift();
        }

        // Update the chart's data
        priceChart.data.labels = labels;
        priceChart.data.datasets[0].data = data;

        // Update the chart
        priceChart.update();
        console.log(priceInfo);
    });
});

function sendUpdateRequest(id) {
    stompClient.send("/app/update", {}, id);
}

let ctx = document.getElementById('priceChart').getContext("2d");

var priceChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: labels,
        datasets: [{
            data: data,
            borderColor: '#07a4ca',
            backgroundColor: '#1a1d1f',
            fill: false,
            borderWidth: 1,
            pointRadius: 0,
            tension: 0
        }]
    },
    options: {
        plugins: {
            legend: {
                display: false // Disable the legend
            }
        },
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Time'
                },
            },
            y: {
                title: {
                    display: true,
                    text: 'Price (USD)'
                },
                beginAtZero: false
            }
        },
        responsive: true,
        maintainAspectRatio: true,
        animation: false
    }
});

document.getElementById("searchInput").addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        event.preventDefault();
        document.querySelector(".search-form").submit();
    }
});