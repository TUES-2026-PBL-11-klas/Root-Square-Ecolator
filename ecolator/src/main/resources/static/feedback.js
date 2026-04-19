const menuButton = document.getElementById("menuButton");
const dropdownMenu = document.getElementById("dropdownMenu");
const accountButton = document.getElementById("accountButton");
const accountMenu = document.getElementById("accountMenu");

if (menuButton && dropdownMenu) {
  menuButton.addEventListener("click", (e) => {
    e.stopPropagation();
    dropdownMenu.classList.toggle("active");
    if (accountMenu) accountMenu.classList.remove("active");
  });
}

if (accountButton && accountMenu) {
  accountButton.addEventListener("click", (e) => {
    e.stopPropagation();
    accountMenu.classList.toggle("active");
    if (dropdownMenu) dropdownMenu.classList.remove("active");
  });
}

document.addEventListener("click", () => {
  if (dropdownMenu) dropdownMenu.classList.remove("active");
  if (accountMenu) accountMenu.classList.remove("active");
});

const savedData = sessionStorage.getItem("ecolatorFeedback");

if (!savedData) {
  window.location.href = "info.html";
} else {
  const data = JSON.parse(savedData);

  document.getElementById("feedbackCo2").textContent =
    `CO₂ Emissions: ${data.co2.toFixed(2)} kg/year`;

  document.getElementById("feedbackEco").textContent =
    `Ecological Footprint: ${data.eco.toFixed(2)} gha`;

  document.getElementById("feedbackPercentileText").textContent =
    `You are in the ${data.percentile}th percentile for air harm.`;

  const recList = document.getElementById("feedbackRecommendations");
  recList.innerHTML = "";

  data.recommendations.forEach((rec) => {
    const li = document.createElement("li");
    li.textContent = rec;
    recList.appendChild(li);
  });

  const marker = document.getElementById("percentileMarker");
  marker.style.left = `${data.percentile}%`;
}