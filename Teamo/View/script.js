const cardViewer = document.getElementById('cardViewer');

const tags = [
  { role: ['web developer', 'frontend developer', '3D artist', 'animator']
  },
  { tools: ['blender', 'maya','adobe suite','photoshop']
  },
  { vibes: ['cute','horror','cool','punk']
  },
]

const profiles = [
  { id: 1,
    name: 'Alice Johnson',
    title: 'Frontend Developer',
    bio: 'Loves building sleek user interfaces.',
    avatar: 'https://i.pravatar.cc/150?img=1',
    banner: 'https://placehold.co/1000x400',
    tags: ['tag1','tag2','tag3','tag4','tag5'],
    portfolio_imgs: ['https://placehold.co/600x400','https://placehold.co/600x400','https://placehold.co/600x400','https://placehold.co/600x400','https://placehold.co/600x400'],
    projects: [] ,
    followers: [],
  },
  { id: 2,
    name: 'Bob Smith',
    title: 'Backend Engineer',
    bio: 'Passionate about server-side logic.',
    avatar: 'https://i.pravatar.cc/150?img=2',
    banner: 'https://placehold.co/1000x400',
    tags: ['tag1','tag2','tag3','tag4','tag5'],
    portfolio_imgs: ['https://placehold.co/600x400','https://placehold.co/600x400','https://placehold.co/600x400','https://placehold.co/600x400','https://placehold.co/600x400'],
    projects: [] ,
    followers: [],
  },
  { id: 3,
    name: 'Charlie Rose',
    title: 'UX Designer',
    bio: 'Crafts experiences with empathy.',
    avatar: 'https://i.pravatar.cc/150?img=3',
    banner: 'https://placehold.co/1000x400',
    tags: ['tag1','tag2','tag3','tag4','tag5'],
    portfolio_imgs: ['https://placehold.co/600x400','https://placehold.co/600x400','https://placehold.co/600x400','https://placehold.co/600x400','https://placehold.co/600x400'],
    projects: [] ,
    followers: [],
  },
  { id: 4,
    name: 'Dana Waterboard',   
    title: 'UI Designer',
    bio: 'Crafts visuals yada yada.',
    avatar: 'https://i.pravatar.cc/150?img=4',
    banner: 'https://placehold.co/1000x400',
    tags: ['tag1','tag2','tag3','tag4','tag5'],
    portfolio_imgs: ['https://placehold.co/600x400','https://placehold.co/600x400','https://placehold.co/600x400','https://placehold.co/600x400','https://placehold.co/600x400'],
    projects: [] ,
    followers: [],
  },
  { id: 5,
    name: 'Eli Steverstein', 
    title: 'Game Dev',
    bio: 'Crafts experiences with hamsters.',
    avatar: 'https://i.pravatar.cc/150?img=5',
    banner: 'https://placehold.co/1000x400',
    tags: ['tag1','tag2','tag3','tag4','tag5'],
    portfolio_imgs: [{img_src: 'https://placehold.co/600x400', title: 'img 1', desc: 'desc 1'}, 
      {img_src: 'https://placehold.co/600x400', title: 'img 2', desc: 'desc 2'}, 
      {img_src: 'https://placehold.co/600x400', title: 'img 3', desc: 'desc 3'}, {img_src: 'https://placehold.co/600x400', title: 'img 4', desc: 'desc 4'},
      {img_src: 'https://placehold.co/600x400', title: 'img 5', desc: 'desc 5'},
    ],
    projects: [] ,
    followers: [],
  },
];

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

// Update the selected tags display
function updateSelectedTags() {
  selectedTagsContainer.innerHTML = selectedTags.map(tag => `<span>${tag}</span>`).join(', ');
}

// Filter and show suggestions
function showSuggestions(query) {
  suggestionsList.innerHTML = '';
  if (!query) return;

  const filtered = availableTags.filter(tag =>
    tag.toLowerCase().includes(query.toLowerCase()) &&
    !selectedTags.includes(tag)
  );

  filtered.forEach(tag => {
    const li = document.createElement('li');
    li.textContent = tag;
    li.addEventListener('click', () => {
      addTag(tag);
    });
    suggestionsList.appendChild(li);
  });
}

// Add a tag to selected list
function addTag(tag) {
  if (!selectedTags.includes(tag)) {
    selectedTags.push(tag);
    updateSelectedTags();
    tagSearch.value = '';
    suggestionsList.innerHTML = '';
  }
}

// Handle input (suggestions)
tagSearch.addEventListener('input', () => {
  const query = tagSearch.value.trim();
  showSuggestions(query);
});


// Handle Enter key
tagSearch.addEventListener('keydown', (e) => {
  if (e.key === 'Enter') {
    e.preventDefault();
    const query = tagSearch.value.trim();
    const match = availableTags.find(tag => tag.toLowerCase() === query.toLowerCase());
    if (match && !selectedTags.includes(match)) {
      addTag(match);
    }
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
    btn.addEventListener('click', () => {
      const tagToRemove = btn.getAttribute('data-tag');
      selectedTags = selectedTags.filter(t => t !== tagToRemove);
      updateSelectedTags();
    });
  });
}

// tag selection section end

let currentIndex = 0;
const cardHeight = 220; // 200px card + 20px margin

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
    // galleryHTML += `<div class="gallery__item">${profile.portfolio_imgs[i]}</div>`;
    const item = profile.portfolio_imgs[i];
    galleryHTML += `
    <div class="gallery__item" id="gallery__item">
      <img src="${item.img_src}" alt="${item.title}" class="gallery__image">
      <div class="gallery__details">
        <h3>${item.title}</h3>
        <p>${item.desc}</p>
      </div>
    </div>
    `;
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
          <p class="profile__stats">Projects: ${profile.projects.length} | Followers: ${profile.followers.length} <button>Message</button></p>
        </div>
      </div>
      </div>
      <p class="profile__tagline">${profile.bio}</p>
      <div class="gallery">
          ${galleryHTML}
      </div> 
  `;
      // Add expand-on-click gallery img functionality
    const galleryItems = document.querySelectorAll('.gallery__item');

    galleryItems.forEach(item => {
      item.addEventListener('click', () => {
        item.classList.toggle('expanded');
      });
    });
    }

renderCardsWindow();
renderProfileWindow();



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