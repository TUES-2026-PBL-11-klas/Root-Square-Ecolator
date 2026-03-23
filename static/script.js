document.getElementById("footprintForm").addEventListener("submit", function(e){
  e.preventDefault();

  const transportKm = parseFloat(document.getElementById("transportKm").value);
  const diet = document.getElementById("diet").value;
  const electricityKwh = parseFloat(document.getElementById("electricityKwh").value);

  const co2 = (transportKm * 0.21) + (electricityKwh * 0.5);
  const eco = co2 / 1000;

  document.getElementById("co2Result").textContent =
    `CO₂ Emissions: ${co2.toFixed(2)} kg/year`;

  document.getElementById("ecoResult").textContent =
    `Ecological Footprint: ${eco.toFixed(2)} gha`;

  const recs = [];

  if (transportKm > 100) {
    recs.push("Consider using public transport or biking more often.");
  }

  if (diet === "omnivore") {
    recs.push("Try reducing meat consumption.");
  }

  if (electricityKwh > 200) {
    recs.push("Use energy-efficient appliances.");
  }

  const recList = document.getElementById("recommendations");
  recList.innerHTML = "";

  recs.forEach(r => {
    const li = document.createElement("li");
    li.textContent = r;
    recList.appendChild(li);
  });
});


const menuButton = document.getElementById("menuButton");
const dropdownMenu = document.getElementById("dropdownMenu");

const accountButton = document.getElementById("accountButton");
const accountMenu = document.getElementById("accountMenu");

menuButton.addEventListener("click", (e) => {
  e.stopPropagation();
  dropdownMenu.classList.toggle("active");
  accountMenu.classList.remove("active");
});

accountButton.addEventListener("click", (e) => {
  e.stopPropagation();
  accountMenu.classList.toggle("active");
  dropdownMenu.classList.remove("active");
});

dropdownMenu.addEventListener("click", (e) => e.stopPropagation());
accountMenu.addEventListener("click", (e) => e.stopPropagation());

document.addEventListener("click", () => {
  dropdownMenu.classList.remove("active");
  accountMenu.classList.remove("active");
});