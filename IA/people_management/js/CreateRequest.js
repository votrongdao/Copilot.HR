/**
 * HR Platform - Create Request Form Logic
 */
document.addEventListener('DOMContentLoaded', function () {
    console.log('CreateRequest JS initialized');

    const typeCards = document.querySelectorAll('.type-card');
    typeCards.forEach(card => {
        card.addEventListener('click', function () {
            typeCards.forEach(c => c.classList.remove('active'));
            card.classList.add('active');
        });
    });

    const startDate = document.getElementById('startDate');
    const endDate = document.getElementById('endDate');
    const calculatedDaysText = document.getElementById('calculatedDaysText');

    function calculateDays() {
        if (!startDate || !endDate || !calculatedDaysText) return;
        const start = new Date(startDate.value);
        const end = new Date(endDate.value);
        if (isNaN(start) || isNaN(end)) return;

        const diffTime = Math.abs(end - start);
        const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1;
        calculatedDaysText.innerText = `${diffDays} Working Days`;
    }

    if (startDate) startDate.addEventListener('change', calculateDays);
    if (endDate) endDate.addEventListener('change', calculateDays);
});
