/* ------------------------------
   FORM SUBMISSION (info.html)
------------------------------ */

const footprintForm = document.getElementById("footprintForm");

if (footprintForm) {
  footprintForm.addEventListener("submit", function (e) {
    e.preventDefault();

    const transportKm = parseFloat(document.getElementById("transportKm").value);
    const diet = document.getElementById("diet").value;
    const electricityKwh = parseFloat(document.getElementById("electricityKwh").value);

    const co2 = (transportKm * 0.21) + (electricityKwh * 0.5);
    const eco = co2 / 1000;

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

    if (recs.length === 0) {
      recs.push("Great job — your lifestyle already has a relatively low environmental impact.");
    }

    /* Simple percentile model */
    let percentile = Math.round((co2 / 200) * 100);

    if (percentile < 1) percentile = 1;
    if (percentile > 99) percentile = 99;

    sessionStorage.setItem(
      "ecolatorFeedback",
      JSON.stringify({
        co2,
        eco,
        recommendations: recs,
        percentile
      })
    );

    window.location.href = "feedback.html";
  });
}


/* ------------------------------
   MENU BUTTONS
------------------------------ */

const menuButton = document.getElementById("menuButton");
const dropdownMenu = document.getElementById("dropdownMenu");

const accountButton = document.getElementById("accountButton");
const accountMenu = document.getElementById("accountMenu");

if (menuButton && dropdownMenu) {
  menuButton.addEventListener("click", (e) => {
    e.stopPropagation();
    dropdownMenu.classList.toggle("active");

    if (accountMenu) {
      accountMenu.classList.remove("active");
    }
  });
}

if (accountButton && accountMenu) {
  accountButton.addEventListener("click", (e) => {
    e.stopPropagation();
    accountMenu.classList.toggle("active");

    if (dropdownMenu) {
      dropdownMenu.classList.remove("active");
    }
  });
}

if (dropdownMenu) {
  dropdownMenu.addEventListener("click", (e) => e.stopPropagation());
}

if (accountMenu) {
  accountMenu.addEventListener("click", (e) => e.stopPropagation());
}

document.addEventListener("click", () => {
  if (dropdownMenu) dropdownMenu.classList.remove("active");
  if (accountMenu) accountMenu.classList.remove("active");
});


/* ------------------------------
   INDEX PAGE SLIDER
------------------------------ */

const monthSlider = document.getElementById("monthSlider");

if (monthSlider) {

  const monthValue = document.getElementById("monthValue");
  const co2Value = document.getElementById("co2Value");
  const bar = document.getElementById("bar");

  const months = [
    "January","February","March","April","May","June",
    "July","August","September","October","November","December"
  ];

  const co2Data = [300, 280, 250, 270, 320, 290, 310, 300, 280, 260, 270, 290];

  function updateSliderDisplay() {

    const monthIndex = parseInt(monthSlider.value);

    monthValue.textContent = months[monthIndex];
    co2Value.textContent = co2Data[monthIndex] + " kg";

    const maxCo2 = Math.max(...co2Data);
    const widthPercent = (co2Data[monthIndex] / maxCo2) * 100;

    bar.style.width = widthPercent + "%";
  }

  monthSlider.addEventListener("input", updateSliderDisplay);

  updateSliderDisplay();
}