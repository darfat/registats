/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import MatchService from '@/entities/match/match.service';
import { Match } from '@/shared/model/match.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('Match Service', () => {
    let service: MatchService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new MatchService();
      currentDate = new Date();

      elemDefault = new Match(
        0,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            date: format(currentDate, DATE_TIME_FORMAT),
            time: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a Match', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: format(currentDate, DATE_TIME_FORMAT),
            time: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            date: currentDate,
            time: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a Match', async () => {
        const returnedFromService = Object.assign(
          {
            date: format(currentDate, DATE_TIME_FORMAT),
            time: format(currentDate, DATE_TIME_FORMAT),
            name: 'BBBBBB',
            description: 'BBBBBB',
            refree: 'BBBBBB',
            liveStreamUrl: 'BBBBBB',
            wheater: 'BBBBBB',
            wind: 'BBBBBB',
            analysis: 'BBBBBB',
            preMatchTalk: 'BBBBBB',
            postMatchTalk: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            time: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of Match', async () => {
        const returnedFromService = Object.assign(
          {
            date: format(currentDate, DATE_TIME_FORMAT),
            time: format(currentDate, DATE_TIME_FORMAT),
            name: 'BBBBBB',
            description: 'BBBBBB',
            refree: 'BBBBBB',
            liveStreamUrl: 'BBBBBB',
            wheater: 'BBBBBB',
            wind: 'BBBBBB',
            analysis: 'BBBBBB',
            preMatchTalk: 'BBBBBB',
            postMatchTalk: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            date: currentDate,
            time: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a Match', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
