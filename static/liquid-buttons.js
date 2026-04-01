const liquidButtons = Array.from(
  document.querySelectorAll("button:not(.menu-button):not(.account-button)")
);

liquidButtons.forEach((button) => {
  if (button.dataset.liquidReady === "true") {
    return;
  }

  button.dataset.liquidReady = "true";
  button.classList.add("liquid-button");
  button.style.setProperty("--liquid-rise", "100%");

  const label = document.createElement("span");
  label.className = "liquid-button__label";

  while (button.firstChild) {
    label.appendChild(button.firstChild);
  }

  const surface = document.createElement("span");
  surface.className = "liquid-button__surface";

  button.appendChild(surface);
  button.appendChild(label);

  for (let index = 0; index < 6; index += 1) {
    const bubble = document.createElement("span");
    bubble.className = "liquid-button__bubble";
    bubble.style.setProperty("--x", `${0.12 + Math.random() * 0.76}`);
    bubble.style.setProperty("--size", `${0.2 + Math.random() * 0.8}`);
    bubble.style.setProperty("--delay", `${Math.random() * 2.8}s`);
    bubble.style.setProperty("--duration", `${2.6 + Math.random() * 1.8}s`);
    bubble.style.setProperty("--drift", `${Math.random()}`);
    button.appendChild(bubble);
  }

});
