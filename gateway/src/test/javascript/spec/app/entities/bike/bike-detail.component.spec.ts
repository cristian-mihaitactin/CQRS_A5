/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GatewayTestModule } from '../../../test.module';
import { BikeDetailComponent } from '../../../../../../main/webapp/app/entities/bike/bike-detail.component';
import { BikeService } from '../../../../../../main/webapp/app/entities/bike/bike.service';
import { Bike } from '../../../../../../main/webapp/app/entities/bike/bike.model';

describe('Component Tests', () => {

    describe('Bike Management Detail Component', () => {
        let comp: BikeDetailComponent;
        let fixture: ComponentFixture<BikeDetailComponent>;
        let service: BikeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [BikeDetailComponent],
                providers: [
                    BikeService
                ]
            })
            .overrideTemplate(BikeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BikeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BikeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Bike(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.bike).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
