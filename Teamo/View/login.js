document.querySelector("form").addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("username").value; // input field is "username"
    const password = document.getElementById("password").value;

    try {
      const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
      });

      if (!res.ok) {
        const err = await res.text();
        alert("Login failed: " + err);
        return;
      }

      const user = await res.json();
      console.log("Logged in user:", user);

      localStorage.setItem("user", JSON.stringify(user));

      // Redirect to profiles page
      window.location.href = "profiles.html";
    } catch (err) {
      console.error("Error logging in:", err);
      alert("Something went wrong. Check console.");
    }
  });