const cardViewer = document.getElementById('cardViewer');

const API_BASE = 'http://localhost:8080/api';


// async function apiGet(path) {
//   const res = await fetch(`${API_BASE}${path}`, {
//     headers: { 'Accept': 'application/json' }
//   });
//   if (!res.ok) throw new Error(`HTTP ${res.status}`);
//   return res.json();
// }

async function apiGet(path) {
  const url = `${API_BASE}${path}`;
  try {
    const res = await fetch(url, { headers: { 'Accept': 'application/json' } });
    const text = await res.text();
    console.log('API response', res.status, url, text.slice(0, 300));
    if (!res.ok) throw new Error(`HTTP ${res.status}: ${text}`);
    try {
      return JSON.parse(text);
    } catch (e) {
      throw new Error('Response was not valid JSON');
    }
  } catch (e) {
    console.error('Fetch failed for', url, e);
    throw e;
  }
}


function mapProfileDTO(p) {
  return {
    id: p.id,
    name: p.name,
    title: p.title,
    bio: p.bio,
    avatar: p.avatar,
    banner: p.banner,
    tags: Array.isArray(p.tags) ? p.tags : [],
    portfolio_imgs: Array.isArray(p.portfolio_imgs) ? p.portfolio_imgs : [],
    //projects: new Array(p.projects || 0).fill(0),
    //followers: new Array(p.followers || 0).fill(0),
  };
}

async function fetchAllProfiles() {
  const data = await apiGet('/profiles');
  return data.map(mapProfileDTO);
}

async function fetchProfilesByTag(tag) {
  const data = await apiGet(`/profiles/search?tag=${encodeURIComponent(tag)}`);
  return data.map(mapProfileDTO);
}

// tag selection section start
const toggleBtn = document.getElementById('toggle-btn');
const tagSection = document.getElementById('tag-section');
const tagSearch = document.getElementById('tag-search');
const selectedTagsContainer = document.getElementById('selected-tags');
const suggestionsList = document.getElementById('suggestions');

// Predefined sample tags for search simulation
const availableTags = ['Frontend Developer', 'Backend Developer', 'Developer','JavaScript', 'HTML', 'CSS', 'React', 'Vue', 'Node.js'];
let selectedTags = [];

// Click button to expand area
toggleBtn.addEventListener('click', () => {
  tagSection.classList.toggle('expanded');
  tagSection.classList.toggle('collapsed');
});

// Listen for Enter key in search input
tagSearch.addEventListener('keypress', async (e) => {
  if (e.key === 'Enter') {
    e.preventDefault();
    const query = tagSearch.value.trim();
    if (query && availableTags.includes(query) && !selectedTags.includes(query)) {
      selectedTags.push(query);
      updateSelectedTags(); 
    }
    tagSearch.value = '';
    await loadProfiles(query);
  }
});

// // Listen for Enter key in search input
// tagSearch.addEventListener('keypress', (e) => {
//   if (e.key === 'Enter') {
//     const query = tagSearch.value.trim();
//     if (query && availableTags.includes(query) && !selectedTags.includes(query)) {
//       selectedTags.push(query);
//       updateSelectedTags();
//       tagSearch.value = '';
//     }
//   }
// });

// Hide suggestions when clicking outside
document.addEventListener('click', (e) => {
  if (!suggestionsList.contains(e.target) && e.target !== tagSearch) {
    suggestionsList.innerHTML = '';
  }
});

function updateSelectedTags() {
  selectedTagsContainer.innerHTML = '';
  selectedTags.forEach(tag => {
    const tagEl = document.createElement('div');
    tagEl.classList.add('tag');
    tagEl.innerHTML = `${tag} <span class="remove" data-tag="${tag}">&times;</span>`;
    selectedTagsContainer.appendChild(tagEl);
  });

  document.querySelectorAll('.remove').forEach(btn => {
    btn.addEventListener('click', async () => {
      const tagToRemove = btn.getAttribute('data-tag');
      selectedTags = selectedTags.filter(t => t !== tagToRemove);
      updateSelectedTags();
      await loadProfiles(selectedTags.at(-1) || null);
    });
  });
}

// tag selection section end

let profiles = []; 
let currentIndex = 0;
const cardHeight = 220; // 200px card + 20px margin

async function loadProfiles(tag = null) {
  try {
    profiles = tag ? await fetchProfilesByTag(tag) : await fetchAllProfiles();
    currentIndex = 0;
    renderCardsWindow();
    renderProfileWindow();
  } catch (err) {
    console.error('Failed to load profiles:', err);
    const detailContainer = document.getElementById('profile-detail');
    if (detailContainer) {
      detailContainer.innerHTML = '<p style="color:#c00">Failed to load profiles.</p>';
    }
  }
}

function renderCardsWindow() {
  cardViewer.innerHTML = '';

  const windowIndexes = [
    currentIndex - 1,
    currentIndex,
    currentIndex + 1,
  ];

  windowIndexes.forEach(index => {
    if (index >= 0 && index < profiles.length) {
      const profile = profiles[index];
      const card = document.createElement('div');
      card.className = 'profile-card';
      
      let tagHTML = '';
      for (let i = 0; i < 3 && i < profile.tags.length; i++) {
        tagHTML += `<div class="sidebar__profile_tags">${profile.tags[i]}</div>`;
      }
      card.innerHTML = `

        <div class="avatar_container">
              <div>
                <img class="sidebar__banner" src=${profile.banner}></img>
              </div>
              <div>
                  <div>
                      <img src="${profile.avatar}" class="sidebar__avatar">
                      </img>
                  </div>
              </div>
          </div>
          <div class="sidebar__tag_container">
             ${tagHTML}
          </div>
          <div class="sidebar__status"> 
              <div>
                <h2>${profile.name}</h2>
                <h4>${profile.title}</h4>
                <p>${profile.bio}</p>
              </div>
          </div>
        </div>
      `;

      const offset = index===currentIndex?0: (index>currentIndex? -cardHeight/2: cardHeight/2) ;
      card.style.transform = `translateY(${offset}px)`;
      if (index > currentIndex) {
        card.classList.add('top');
      }
      if (index < currentIndex) {
        card.classList.add('bottom');
      }

      cardViewer.appendChild(card);
    }
  });
}

function moveToNextCard() {
  if (currentIndex < profiles.length - 1) {
    currentIndex++;
    updateCards();
    renderCardsWindow();  
    renderProfileWindow(); // <== update the detail view too
  }
}

function moveToPrevCard() {
  if (currentIndex > 0) {
    currentIndex--;
    renderCardsWindow();  // <== update the cards
    renderProfileWindow(); // <== update the detail view too
  }
}

function updateCards() {
  const cards = document.querySelectorAll('.profile-card');
  cards.forEach((card, index) => {
    const offset = index===currentIndex?0: (index>currentIndex? -cardHeight/2: cardHeight/2) ;
    card.style.transform = `translateY(${offset}px)`;
    if (index === currentIndex) {
      card.classList.remove('top');
      card.classList.remove('bottom');
    } 
    else if (index > currentIndex) {
      card.classList.add('top');
    } 
    else if (index < currentIndex) {
      card.classList.add('bottom');
    }
  });
}

function renderProfileWindow() {
const detailContainer = document.getElementById('profile-detail');
  const profile = profiles[currentIndex];

  if (!profile) {
    detailContainer.innerHTML = '<p>No profile selected.</p>';
    return;
  }

  let galleryHTML = '';
  for (let i = 0; i < profile.portfolio_imgs.length; i++) {
    const url = profile.portfolio_imgs[i];
    galleryHTML += `<div class="gallery__item"><img src="${url}" alt="portfolio ${i+1}"></div>`;
  }

  detailContainer.innerHTML = `
    <div class="avatar_container">
    <div>
      <img class="profile__banner" src=${profile.banner}>
      </img>
    </div>
      <div class="profile__info">
        <div>
          <img src="${profile.avatar}" alt="${profile.name}" class="detail-avatar">
        </div>
        <div>
          <h1 class="profile__name">${profile.name} | ${profile.title}</h1>
          // <p class="profile__stats">Projects:  | Followers:  <button>Message</button></p>
        </div>
      </div>
      <div>
        <h1 class="profile__name">${profile.name} | ${profile.title}</h1>
        <p class="profile__stats">Projects:  | Followers:  <button>Message</button></p>
      </div>
    </div>
    </div>
    <p class="profile__tagline">${profile.bio}</p>
    <div class="gallery">
        ${galleryHTML}
    </div> 
    <div id="imageModal" class="modal">
      <span class="close-btn">&times;</span>
      <div class="parent">
        <img class="modal-content" id="fullImage" />
        <!-- <div id="caption">aaaa</div> -->
      </div>
    </div>
  `;
      // Add expand-on-click gallery img functionality
    // const galleryItems = document.querySelectorAll('.gallery__item');

    // galleryItems.forEach(item => {
    //   item.addEventListener('click', () => {
    //     item.classList.toggle('expanded');
    //   });
    // });
    // Get the modal and its elements
    const modal = document.getElementById("imageModal");
    const fullImage = document.getElementById("fullImage");
    // const caption = document.getElementById("caption");
    const closeBtn = document.querySelector(".close-btn");
    const galleryImages = document.querySelectorAll(".gallery img");

    // Click event listener to each gallery image
    galleryImages.forEach(image => {
      image.addEventListener("click", () => {
        // Get full image path from the data-full attribute
        const fullImagePath = image.getAttribute('src');

        // Update the modal's image source
        fullImage.src = fullImagePath;
        const imgSrcToFind = fullImagePath;
        // const capt_item = profile.portfolio_imgs.find(img => img.img_src === imgSrcToFind);
        // let captionHTML = '';
        // captionHTML += `
        //   <p> 
        //   ${capt_item.title} ${capt_item.desc}
        //   </p>
        // `;
        // Show modal
        modal.style.display = "block";
      });
    });
    // Close modal when close button clicked
    closeBtn.addEventListener("click", () => {
      modal.style.display = "none";
    });

    // Close modal if clicks anywhere outside image
    window.addEventListener("click", (event) => {
      if (event.target === modal) {
        modal.style.display = "none";
      }
    });

    }

// renderCardsWindow();
// renderProfileWindow();

// Scroll interaction
let scrollTimeout;
cardViewer.addEventListener('wheel', (e) => {
  clearTimeout(scrollTimeout);
  scrollTimeout = setTimeout(() => {
    if (e.deltaY > 0) moveToNextCard();
    else moveToPrevCard();
  }, 100);
});

// Click interaction
cardViewer.addEventListener('click', (e) => {
  const viewerRect = cardViewer.getBoundingClientRect();
  const clickY = e.clientY - viewerRect.top;
  const cardCenterY = cardHeight;

  if (clickY < cardCenterY) {
    moveToPrevCard();
  } else {
    moveToNextCard();
  }
});

// Keyboard interaction
document.addEventListener('keydown', (e) => {
  if (e.key === 'ArrowDown') {
    moveToNextCard();
  } else if (e.key === 'ArrowUp') {
    moveToPrevCard();
  }
});

document.addEventListener('DOMContentLoaded', () => {
  // initial load — all active profiles
  loadProfiles();
});