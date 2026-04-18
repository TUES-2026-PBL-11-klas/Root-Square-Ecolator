/* ------------------------------
   FORM SUBMISSION (info.html)
------------------------------ */

const footprintForm = document.getElementById("footprintForm");
const wizardSteps = Array.from(document.querySelectorAll(".wizard-step"));
const wizardIndicators = Array.from(document.querySelectorAll("[data-step-indicator]"));
const prevStepButton = document.getElementById("prevStepButton");
const nextStepButton = document.getElementById("nextStepButton");
const submitWizardButton = document.getElementById("submitWizardButton");
const fuelUsageFields = document.getElementById("fuelUsageFields");
const fuelUsageInputs = fuelUsageFields ? Array.from(fuelUsageFields.querySelectorAll("input")) : [];
const fuelUsageRadios = Array.from(document.querySelectorAll('input[name="knowsFuelUsage"]'));
let currentStepIndex = 0;

function updateFuelUsageVisibility() {
  if (!fuelUsageFields) {
    return;
  }

  const selectedFuelKnowledge = document.querySelector('input[name="knowsFuelUsage"]:checked')?.value;
  const knowsFuelUsage = selectedFuelKnowledge === "yes";

  fuelUsageFields.classList.toggle("hidden", !knowsFuelUsage);

  fuelUsageInputs.forEach((input) => {
    input.required = knowsFuelUsage;

    if (!knowsFuelUsage) {
      input.value = "";
    }
  });
}

function renderWizardStep() {
  wizardSteps.forEach((step, index) => {
    step.classList.toggle("active", index === currentStepIndex);
  });

  wizardIndicators.forEach((indicator, index) => {
    indicator.classList.toggle("active", index === currentStepIndex);
    indicator.classList.toggle("completed", index < currentStepIndex);
  });

  if (prevStepButton) {
    prevStepButton.classList.toggle("hidden", currentStepIndex === 0);
  }

  if (nextStepButton) {
    nextStepButton.classList.toggle("hidden", currentStepIndex === wizardSteps.length - 1);
  }

  if (submitWizardButton) {
    submitWizardButton.classList.toggle("hidden", currentStepIndex !== wizardSteps.length - 1);
  }
}

function validateCurrentStep() {
  const currentStep = wizardSteps[currentStepIndex];

  if (!currentStep) {
    return true;
  }

  const fields = Array.from(currentStep.querySelectorAll("input, select, textarea"));
  return fields.every((field) => {
    if (field.closest(".hidden")) {
      return true;
    }

    return field.reportValidity();
  });
}

if (footprintForm) {
  renderWizardStep();
  updateFuelUsageVisibility();

  nextStepButton?.addEventListener("click", () => {
    if (!validateCurrentStep()) {
      return;
    }

    currentStepIndex += 1;
    renderWizardStep();
  });

  prevStepButton?.addEventListener("click", () => {
    currentStepIndex -= 1;
    renderWizardStep();
  });

  fuelUsageRadios.forEach((radio) => {
    radio.addEventListener("change", updateFuelUsageVisibility);
  });

  footprintForm.addEventListener("submit", async function (e) {
    e.preventDefault();

    if (!validateCurrentStep()) {
      return;
    }

    const cityTransportKm = parseFloat(document.getElementById("cityTransportKm").value);
    const outsideCityTransportKm = parseFloat(document.getElementById("outsideCityTransportKm").value);
    const selectedFuelKnowledge = document.querySelector('input[name="knowsFuelUsage"]:checked')?.value;
    const knowsFuelUsage = selectedFuelKnowledge === "yes";
    const cityFuelLitersPer100Km = knowsFuelUsage
      ? parseFloat(document.getElementById("cityFuelLitersPer100Km").value)
      : 0;
    const outsideCityFuelLitersPer100Km = knowsFuelUsage
      ? parseFloat(document.getElementById("outsideCityFuelLitersPer100Km").value)
      : 0;
    const fuelType = document.getElementById("fuelType").value;
    const diet = document.getElementById("diet").value;
    const electricityKwh = parseFloat(document.getElementById("electricityKwh").value);
    const chatbotPrompt = document.getElementById("chatbotPrompt").value.trim();

    const requestData = {
      cityTransportKm,
      outsideCityTransportKm,
      cityFuelLitersPer100Km,
      outsideCityFuelLitersPer100Km,
      fuelType,
      diet,
      electricityKwh
    };

    try {
      const response = await fetch("http://localhost:3001/api/emissions/calculate", {
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
      sessionStorage.setItem("ecolatorChatPrompt", chatbotPrompt);
      window.location.href = "chatbot.html";

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
