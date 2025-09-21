const cardViewer = document.getElementById('cardViewer');

const profiles = [
  {
    name: 'Alice Johnson',
    title: 'Frontend Developer',
    bio: 'Loves building sleek user interfaces.',
    avatar: 'https://i.pravatar.cc/150?img=1'
  },
  {
    name: 'Bob Smith',
    title: 'Backend Engineer',
    bio: 'Passionate about server-side logic.',
    avatar: 'https://i.pravatar.cc/150?img=2'
  },
  {
    name: 'Charlie Rose',
    title: 'UX Designer',
    bio: 'Crafts experiences with empathy.',
    avatar: 'https://i.pravatar.cc/150?img=3'
  },
  { name: 'Dana Waterboard',   
    title: 'UI Designer',
    bio: 'Crafts visuals yada yada.',
    avatar: 'https://i.pravatar.cc/150?img=4'
  },
  { name: 'Eli Steverstein', 
    title: 'Game Dev',
    bio: 'Crafts experiences with hamsters.',
    avatar: 'https://i.pravatar.cc/150?img=5'
  },
];

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
      
      card.innerHTML = `

        <div class="avatar_container">
              <div class="sidebar__banner">Banner IMG</div>
              <div>
                  <div>
                      <img src="${profile.avatar}" class="sidebar__avatar">
                      </img>
                  </div>
              </div>
          </div>
          <div class="sidebar__tag_container">
              <div class="sidebar__profile_tags">Top Tag 1</div>
              <div class="sidebar__profile_tags">Top Tag 2</div>
              <div class="sidebar__profile_tags">Top Tag 3</div>
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
  }
}

function moveToPrevCard() {
  if (currentIndex > 0) {
    currentIndex--;
    renderCardsWindow();
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

renderCardsWindow();

// // Scroll interaction
// let scrollTimeout;
// cardViewer.addEventListener('wheel', (e) => {
//   clearTimeout(scrollTimeout);
//   scrollTimeout = setTimeout(() => {
//     if (e.deltaY > 0) moveToNextCard();
//     else moveToPrevCard();
//   }, 100);
// });

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