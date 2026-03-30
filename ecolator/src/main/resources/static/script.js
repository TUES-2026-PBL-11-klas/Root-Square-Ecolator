/* ------------------------------
   FORM SUBMISSION (info.html)
------------------------------ */

const footprintForm = document.getElementById("footprintForm");

if (footprintForm) {
  footprintForm.addEventListener("submit", async function (e) {
    e.preventDefault();

    const cityTransportKm = parseFloat(document.getElementById("cityTransportKm").value);
    const outsideCityTransportKm = parseFloat(document.getElementById("outsideCityTransportKm").value);
    const diet = document.getElementById("diet").value;
    const electricityKwh = parseFloat(document.getElementById("electricityKwh").value);

    const requestData = {
      cityTransportKm,
      outsideCityTransportKm,
      diet,
      electricityKwh
    };

    try {
      const response = await fetch("http://localhost:8080/api/emissions/calculate", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(requestData)
      });

      if (!response.ok) {
        throw new Error("Failed to calculate emissions.");
      }

      const result = await response.json();

      sessionStorage.setItem("ecolatorFeedback", JSON.stringify(result));
      window.location.href = "feedback.html";

    } catch (error) {
      console.error(error);
      alert("Could not connect to the backend.");
    }
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